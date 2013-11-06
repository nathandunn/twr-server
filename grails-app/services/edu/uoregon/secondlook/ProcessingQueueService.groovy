package edu.uoregon.secondlook

class ProcessingQueueService {

    def totalWordReadService

//    String baseProcessingDirectory ="/usr/share/tomcat/temp"
    String baseProcessingDirectory =System.getenv("CATALINA_TEMP") ?: "/usr/share/tomcat/temp"
    String decodeBinary = "/usr/share/kaldi-decode-childspeech/childspeech/s5/run_decode.sh"
    String timingsFile = "timings.all.txt"

    def submitTranscript(Long id) {
        Transcription transcription = Transcription.get(id)
        println "got transcription value ${transcription}"
        println "processing in directory ${baseProcessingDirectory}"


        if (transcription) {
            ProcessingQueue processingQueue = ProcessingQueue.findByTranscription(transcription)
            if (!processingQueue) {
                processingQueue = new ProcessingQueue(
                        transcription: transcription
                        , entryDate: new Date()
                        , status: ProcessingStatus.DELIVERED
                ).save(insert: true)
            }
            transcription.addToProcessingQueues(processingQueue)
            transcription.status = TranscriptionStatus.SUBMITTED
            transcription.save(flush: true)
//            return transcription.status

            println "Pre-processing transcript"
            processTranscriptAsync(processingQueue)
            println "POST-processing transcript"

        } else {
            return null
        }
    }

    def processTranscriptAsync(ProcessingQueue processingQueue) {
        def resultOutput
        runAsync{
            println "start ASync processing"
            resultOutput = processTranscript(processingQueue)
            println "after ASync processing"
            Transcription transcription = processingQueue.transcription
            transcription.transcript = resultOutput
            transcription.twr = totalWordReadService.calculateTotalWordsRead(processingQueue.transcription)
            processingQueue.status = ProcessingStatus.FINISHED
            transcription.status = TranscriptionStatus.FINISHED

            transcription.save(flush: true)
            processingQueue.save(flush: true)
        }

//        def future = callAsync {
//            processTranscript(processingQueue)
//        }


//        def resultOutput = future.get()
    }

    def processTranscript(ProcessingQueue processingQueue) {

        println "starting on Transcript ${processingQueue.transcription.fileName}"

        // TODO: get directory from configuration
        Transcription transcription = processingQueue.transcription

        println "got transcript for filename ${transcription.fileName}"

        // TODO: create directory using transcript unique name
        println "passage ${transcription?.passage?.name}"
        String uniqueId = transcription.externalStudentId + transcription.passage.externalId
        println "unique ID: ${uniqueId}"
        String processingDirectory = baseProcessingDirectory + "/"+uniqueId+"/"

        println "processing directory ${processingDirectory}"

        File file = new File(processingDirectory)
        if(file.mkdir())
        assert file.exists()
        assert file.isDirectory()


        // TODO: write audio data to disk
        byte[] audioData = transcription.audioData;
        String decodeFile = processingDirectory+"/decodable.wav"
        FileOutputStream fileOutputStream = new FileOutputStream(new File(decodeFile))
        fileOutputStream.write(audioData)
        fileOutputStream.close()


        // ??
        // TODO: use SOX/LAME convert from android multimedia 44kHz.wav Mono to 16Hz Mono WAV file
        // TODO: use SOX/LAME convert from android multimedia .amr to 16kHz Mono WAV file




        println "STARTED doing some exec process for 20 seconds"

        // TODO: exec processes go here . . . tell it where to write stuff
//        String command = """${decodeBinary} mono0a ${processingDirectory}"""
//        def proc = command.execute()                 // Call *execute* on the string
        String execString = [decodeBinary,processingDirectory].join(" ")
//        String execString = ["ls"].join(" ")
        println "execString ${execString}"
        Process proc
        try {
            proc = execString.execute()
        } catch (e) {
            println "error ${e}"
        }
        println "output ${proc.in.text}"
        println "error ${proc.err.text}"
//        println "process ${proc}"
//        def command = """executable arg1 arg2 arg3"""// Create the String
        int status = proc.waitFor()
        println "finished with status ${status}"


//        sleep 20000

        println "FINISHED some exec process for 20 seconds"

        String timingResultFile = processingDirectory+"/"+timingsFile
        File resultFile = new File(timingResultFile)
        if(resultFile.exists()){
            return file.text
        }
        else{
            println "Timings file does not exist ${timingResultFile}"
            return ""
        }
    }
}

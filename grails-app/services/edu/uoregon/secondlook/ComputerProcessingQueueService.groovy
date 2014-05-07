package edu.uoregon.secondlook

import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse

//@CompileStatic
class ComputerProcessingQueueService {

    def totalWordReadService


//    String baseProcessingDirectory ="/usr/share/tomcat/temp"
    String baseProcessingDirectory = System.getenv("CATALINA_TEMP") ?: "/usr/share/tomcat/temp"
    String decodeBinary = "/usr/share/kaldi-decode-childspeech/childspeech/s5-decode/run_decode.sh"
    String timingsFile = "timings.all.txt"
    String soxBinary = "sox"

    def submitTranscript(Long id) {
        ComputerTranscript computerTranscript = ComputerTranscript.get(id)
        println "got transcription value ${computerTranscript}"
        println "processing in directory ${baseProcessingDirectory}"


        if (computerTranscript) {
            println "is a computer transcript"
            ProcessingQueue processingQueue = ProcessingQueue.findByComputerTranscript(computerTranscript)
            println "is there a processing Queue? ${processingQueue}"
            if (!processingQueue) {
                println "creating one ${processingQueue}"
                processingQueue = new ProcessingQueue(
                        computerTranscript: computerTranscript
                        , entryDate: new Date()
                        , status: ProcessingStatus.DELIVERED
                ).save(insert: true, failOnError: true)
                println "created one ${processingQueue}"
            } else {
                processingQueue.status = ProcessingStatus.DELIVERED
                processingQueue.save(flush: true)
            }
            computerTranscript.processingQueue = processingQueue
            computerTranscript.status = TranscriptionStatus.SUBMITTED
            computerTranscript.save(flush: true, failOnError: true)
            println "saved a computer transcript ${computerTranscript}"
        } else {
            println "no computer transcript found I guess"
            return null
        }
    }

    def processTranscriptAsync(Long processingQueueId) {
        def resultOutput
        runAsync {
            ProcessingQueue processingQueue = ProcessingQueue.findById(processingQueueId)
            processingQueue.status = ProcessingStatus.PROCESSING
            processingQueue.save(flush: true)
            println "start ASync processing"
            resultOutput = processTranscript(processingQueue)
            println "after ASync processing"
            ComputerTranscript computerTranscript = processingQueue.computerTranscript
            println "got transcription "
            computerTranscript.transcript = resultOutput
            println "set trancript ${resultOutput.size()}"
            computerTranscript.twr = totalWordReadService.calculateTotalWordsReadFromComputerTranscript(computerTranscript)
            println "calc words read ${computerTranscript.twr}"
            processingQueue.status = ProcessingStatus.FINISHED
            computerTranscript.status = TranscriptionStatus.FINISHED

            computerTranscript.save(flush: true)
            processingQueue.save(flush: true)


            if (computerTranscript.audioFile.callbackUrl) {
                println "doing callback url "
                RestResponse resp = doCallback(computerTranscript)
                println "geting response ?"
                println "status ${resp.status}"

                if (resp.status == 200) {
                    computerTranscript.status = TranscriptionStatus.CALLBACK_OK
                } else {
                    computerTranscript.status = TranscriptionStatus.CALLBACK_ERROR
                }
                println "response text ${resp.text}"

                computerTranscript.save(flush: true)

                println "saved status ${computerTranscript.status}"

//        println "return vlue ${resp.json.submitted}"
//                assert resp.json.submitted!=null
            } else {
                println "no callbcak url so not calling ${computerTranscript.audioFile.fileName} ${computerTranscript.id}"
            }
        }

//        def future = callAsync {
//            processTranscript(processingQueue)
//        }

//        def resultOutput = future.get()
    }

    def doCallback(ComputerTranscript transcription) {
        RestBuilder rest = new RestBuilder()
        RestResponse resp = rest.post(transcription.audioFile.callbackUrl) {
            contentType "multipart/form-data"
            transcriptId = transcription.id as String
            studentId = transcription.audioFile.externalStudentId
            passageId = transcription.audioFile.passage.externalId
            twr = transcription.twr as String
            token = "Yi934nsVA3Nej03h"
        }
        return resp
    }


    def processTranscript(ProcessingQueue processingQueue) {

        println "starting on Transcript ${processingQueue.computerTranscript.audioFile.fileName}"

        // TODO: get directory from configuration
        ComputerTranscript computerTranscript = processingQueue.computerTranscript

        println "got transcript for filename diggity ${computerTranscript.audioFile.fileName}"

        // Passage passage = transcription.passage
//        Passage passage = Passage.executeQuery("select p from ComputerTranscript t join t.audioFile.passage p where t=:transcript", [transcript: computerTranscript], [max: 1])?.get(0)
        Passage passage = computerTranscript.audioFile.passage

        // TODO: create directory using transcript unique name
        println "passage to get? "
        println "passage ${passage?.name}"

        String dateString = new Date().format("yyyyMMddHHmmss")
        String uniqueId = computerTranscript.audioFile.externalStudentId + "-" + passage.externalId + "-" + dateString
        println "unique ID: ${uniqueId}"
        String processingDirectory = baseProcessingDirectory + "/" + uniqueId + "/"
        computerTranscript.processingDirectory = processingDirectory

        println "processing directory ${processingDirectory}"

        File file = new File(processingDirectory)
        if (file.mkdir())
            assert file.exists()
        assert file.isDirectory()

        // TODO: write audio data to disk
        byte[] audioData = computerTranscript.audioFile.audioData;
        String inputFilePath = processingDirectory + "input.wav"
        File inputFile = new File(inputFilePath)
        println "Removing old file ${inputFile.delete()}"
        FileOutputStream fileOutputStream = new FileOutputStream(inputFile)
        fileOutputStream.write(audioData)
        fileOutputStream.close()

        // ??
        // TODO: use SOX/LAME convert from android multimedia 44kHz.wav Mono to 16Hz Mono WAV file
        // TODO: use SOX/LAME convert from android multimedia .amr to 16kHz Mono WAV file

        String decodeFilePath = processingDirectory + "decodable.wav"
        File decodeFile = new File(decodeFilePath)
        println "removing old output file ${decodeFile.delete()}"

//        if(new File(soxBinary).exists()){
        String execSox = [soxBinary, inputFilePath, "-b", "16", decodeFilePath, "channels", "1", "rate", "16k"].join(" ")
        Process procSox
        try {
            procSox = execSox.execute()
        } catch (e) {
            println "error doing SOX ${e}"
            computerTranscript.status = TranscriptionStatus.ERROR
            computerTranscript.save()
            processingQueue.status = ProcessingStatus.ERROR
            processingQueue.save(flush: true)
            return ""
        }

        println "output ${procSox.in.text}"
        println "error ${procSox.err.text}"
//        println "process ${proc}"
//        def command = """executable arg1 arg2 arg3"""// Create the String
        int statusSox = procSox.waitFor()
        println "sox finish with status ${statusSox}"

        /**
         * Move and replace the file back
         */
        assert inputFile.delete()
        DataOutputStream dataOutputStream = inputFile.newDataOutputStream()
        DataInputStream dataInputStream = decodeFile.newDataInputStream()
        dataOutputStream << dataInputStream
        dataOutputStream.close()
        dataInputStream.close()

//        }
//        else{
//            log.error "SOX NOT Found, just copying! "
//            File inputFile = new File(inputFilePath)
//            File outputFile = new File(decodeFile)
//            def input = inputFile.newDataInputStream()
//            def output = outputFile.newDataOutputStream()
//
//            output << input
//
//            input.close()
//            output.close()
//        }

        println "STARTED doing some exec process for 20 seconds"

        // TODO: exec processes go here . . . tell it where to write stuff
//        String command = """${decodeBinary} mono0a ${processingDirectory}"""
//        def proc = command.execute()                 // Call *execute* on the string
        //String execString = [decodeBinary,processingDirectory,8].join(" ")

        String execString
        if (computerTranscript?.transcriptionEngine?.lookup) {
            execString = [computerTranscript.transcriptionEngine.lookup, processingDirectory].join(" ")
        } else {
            execString = [decodeBinary, processingDirectory].join(" ")
        }
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

        String timingResultFile = processingDirectory + "/" + timingsFile
        File resultFile = new File(timingResultFile)
        if (resultFile.exists()) {
            println "returnign file ${resultFile.path}"
            return resultFile.text
        } else {
            println "Timings file does not exist ${timingResultFile}"
            processingQueue.status = ProcessingStatus.ERROR
            processingQueue.save(flush: true)
            computerTranscript.status = TranscriptionStatus.ERROR
            computerTranscript.save(flush: true)
            return ""
        }
    }

    Float findTwrDifference(TranscriptionEngine transcriptionEngine) {

        Float averageHumanTwr = calculateAverageHumanTwrForTranscriptionEngine(transcriptionEngine)
        Float averageComputerTwr = calculateAverageComputerTwrForTranscriptionEngine(transcriptionEngine)

        return Math.abs(averageHumanTwr - averageComputerTwr)
    }

    TranscriptionEngineStatistics calculateAverageComputerTwrForTranscriptionEngineAndPassage(TranscriptionEngine transcriptionEngine,Passage passage) {
        List<ComputerTranscript> computerTranscriptList = ComputerTranscript.findAllByTranscriptionEngine(transcriptionEngine)

        Integer computerTranscriptsTwr = 0
        Integer computerTranscriptCount = 0
        for (ComputerTranscript computerTranscript in computerTranscriptList) {
            if(computerTranscript.audioFile.passage==passage  && computerTranscript.audioFile.humanTranscripts){
                if(computerTranscript.twr){
                    ++computerTranscriptCount
                    computerTranscriptsTwr += computerTranscript.twr
                }
            }
        }

        TranscriptionEngineStatistics transcriptionEngineStatistics = new TranscriptionEngineStatistics()
        transcriptionEngineStatistics.computerTwr = computerTranscriptsTwr / (float) computerTranscriptCount
        transcriptionEngineStatistics.computerCount = computerTranscriptCount
        return transcriptionEngineStatistics
    }

    TranscriptionEngineStatistics calculateAverageHumanTwrForTranscriptionEngineAndPassage(TranscriptionEngine transcriptionEngine,Passage passage) {

        List<AudioFile> audioFileList = AudioFile.executeQuery("select distinct af from ComputerTranscript ct join ct.audioFile af " +
                " where ct.transcriptionEngine = :transcriptionEngine and af.humanTranscripts is not empty  and af.computerTranscripts is not empty and af.passage = :passage"
                , ["transcriptionEngine": transcriptionEngine,"passage":passage])

        Integer humanTranscriptsTwr = 0
        Integer humanTranscriptCount = 0
        for (AudioFile audioFile in audioFileList) {
            if(audioFile.computerTranscripts && audioFile.humanTranscripts){
                for(HumanTranscript humanTranscript in audioFile.humanTranscripts){
                    if(humanTranscript.twr){
                        ++humanTranscriptCount
                        humanTranscriptsTwr += humanTranscript.twr
                    }
                }
            }
        }

        TranscriptionEngineStatistics transcriptionEngineStatistics = new TranscriptionEngineStatistics()
        transcriptionEngineStatistics.humanTwr = humanTranscriptsTwr / (float) humanTranscriptCount
        transcriptionEngineStatistics.humanCount = humanTranscriptCount
        return transcriptionEngineStatistics
    }


    Float calculateAverageComputerTwrForTranscriptionEngine(TranscriptionEngine transcriptionEngine) {
        List<ComputerTranscript> computerTranscriptList = ComputerTranscript.findAllByTranscriptionEngine(transcriptionEngine)

        Integer computerTranscriptsTwr = 0
        Integer computerTranscriptCount = 0
        for (ComputerTranscript computerTranscript in computerTranscriptList) {
            if(computerTranscript.audioFile.humanTranscripts){
                if(computerTranscript.twr){
                    ++computerTranscriptCount
                    computerTranscriptsTwr += computerTranscript.twr
                }
            }
        }

        return computerTranscriptsTwr / (float) computerTranscriptCount
    }

    Float calculateAverageHumanTwrForTranscriptionEngine(TranscriptionEngine transcriptionEngine) {

        List<AudioFile> audioFileList = AudioFile.executeQuery("select distinct af from ComputerTranscript ct join ct.audioFile af " +
                " where ct.transcriptionEngine = :transcriptionEngine and af.humanTranscripts is not empty  and af.computerTranscripts is not empty ", ["transcriptionEngine": transcriptionEngine])

        Integer humanTranscriptsTwr = 0
        Integer humanTranscriptCount = 0
        for (AudioFile audioFile in audioFileList) {
            if(audioFile.computerTranscripts && audioFile.humanTranscripts){
                for(HumanTranscript humanTranscript in audioFile.humanTranscripts){
                    if(humanTranscript.twr){
                        ++humanTranscriptCount
                        humanTranscriptsTwr += humanTranscript.twr
                    }
                }
            }
        }

        return humanTranscriptsTwr / (float) humanTranscriptCount
    }

    Integer findErrorDifference(TranscriptionEngine transcriptionEngine) {
        return 12
    }

    TranscriptionEngineStatistics calculateTwrTranscriptionEngineAndPassage(TranscriptionEngine transcriptionEngine, Passage passage) {
        List<AudioFile> audioFileList = AudioFile.executeQuery("select distinct af from ComputerTranscript ct join ct.audioFile af " +
                " where ct.transcriptionEngine = :transcriptionEngine and af.humanTranscripts is not empty  and af.computerTranscripts is not empty and af.passage = :passage"
                , ["transcriptionEngine": transcriptionEngine,"passage":passage])

        Integer humanTranscriptsTwr = 0
        Integer humanTranscriptCount = 0
        for (AudioFile audioFile in audioFileList) {
            if(audioFile.computerTranscripts && audioFile.humanTranscripts){
                for(HumanTranscript humanTranscript in audioFile.humanTranscripts){
                    if(humanTranscript.twr){
                        ++humanTranscriptCount
                        humanTranscriptsTwr += humanTranscript.twr
                    }
                }
            }
        }

        TranscriptionEngineStatistics transcriptionEngineStatistics = new TranscriptionEngineStatistics()
        transcriptionEngineStatistics.humanTwr = humanTranscriptsTwr / (float) humanTranscriptCount
        transcriptionEngineStatistics.humanCount = humanTranscriptCount

        List<ComputerTranscript> computerTranscriptList = ComputerTranscript.findAllByTranscriptionEngine(transcriptionEngine)
        Integer computerTranscriptsTwr = 0
        Integer computerTranscriptCount = 0
        for (ComputerTranscript computerTranscript in computerTranscriptList) {
            if(computerTranscript.audioFile.passage==passage  && computerTranscript.audioFile.humanTranscripts){
                if(computerTranscript.twr){
                    ++computerTranscriptCount
                    computerTranscriptsTwr += computerTranscript.twr
                }
            }
        }

        transcriptionEngineStatistics.computerTwr = computerTranscriptsTwr / (float) computerTranscriptCount
        transcriptionEngineStatistics.computerCount = computerTranscriptCount
        return transcriptionEngineStatistics
    }
}

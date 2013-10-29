package edu.uoregon.secondlook

class ProcessingQueueService {

    def totalWordReadService

    def submitTranscript(Integer id) {
        Transcription transcription = Transcription.get(id)
        println "got transcript ${transcription}"

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

            processTranscriptAsync(processingQueue)

        } else {
            return null
        }
    }

    def processTranscriptAsync(ProcessingQueue processingQueue) {
        def resultOutput
        runAsync{
            resultOutput = processTranscript(processingQueue)
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

        byte[] audioData = processingQueue.transcription.audioData;

        // TODO: get directory from configuration


        // TODO: create directory using transcript unique name


        // TODO: write audio data to disk


        // ??
        // TODO: use SOX/LAME convert from android multimedia 44kHz.wav Mono to 16Hz Mono WAV file
        // TODO: use SOX/LAME convert from android multimedia .amr to 16kHz Mono WAV file



        // TODO: exec processes go here . . . tell it where to write stuff

        println "STARTED doing some exec process for 20 seconds"

        sleep 20000

        println "FINISHED some exec process for 20 seconds"

//        Transcription transcription = processingQueue.transcription
        return "You have observed an Osprey goo grr pop click"


    }
}

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
            transcription.twr = totalWordReadService.calculateTotalWordsRead(processingQueue.transcription.transcript)
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
        // TODO: write audio data to disk

        // TODO: exec processes go here

        println "STARTED doing some exec process for 20 seconds"

        sleep 20000

        println "FINISHED some exec process for 20 seconds"

//        Transcription transcription = processingQueue.transcription
        return "You have observed an Osprey goo grr pop click"


    }
}

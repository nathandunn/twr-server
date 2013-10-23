package edu.uoregon.secondlook

class ProcessingQueueService {

    def submitTranscript(Integer id) {
        Transcription transcription = Transcription.get(id)

        if (transcription) {
            ProcessingQueue processingQueue = ProcessingQueue.findByTranscription(transcription)
            if (!processingQueue) {
                processingQueue = new ProcessingQueue(
                        transcription: transcription
                        , entryDate: new Date()
                        , status: ProcessingStatus.DELIVERED
                ).save(insert: true)
            }
            transcription.status = TranscriptionStatus.SUBMITTED
            transcription.save(flush: true)
//            return transcription.status


        }
        else{
            return null
        }


    }
}

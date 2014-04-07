package edu.uoregon.secondlook

/**
 */
class CheckQueueJob {

    def processingQueueService

    Integer MAX_QUEUE_SIZE = 2

    static triggers = {
      simple repeatInterval: 30000l // execute job once in 30 seconds
    }

    /**
     * We check for
     * @return
     */
    def execute() {
        // execute job

        Integer currentlyProcessing = ProcessingQueue.countByStatus(ProcessingStatus.PROCESSING)
        if(currentlyProcessing>=MAX_QUEUE_SIZE){
            println "processing max ${MAX_QUEUE_SIZE}"
            return
        }




        List<Transcription> transcriptionList = Transcription.findAllByStatusInList([TranscriptionStatus.RECEIVED],[max:MAX_QUEUE_SIZE-currentlyProcessing])
        for(Transcription transcription in transcriptionList){
            println "adding Transcript to process ${transcription.fileName}"
            processingQueueService.submitTranscript(transcription.id)
        }





        List<ProcessingQueue> processingQueueList = ProcessingQueue.findAllByStatusInList([ProcessingStatus.DELIVERED],[max:MAX_QUEUE_SIZE-currentlyProcessing])
        Integer processesAdded = processingQueueList.size()
//        println "processes to process ${processesAdded}"

        for(ProcessingQueue processingQueue in processingQueueList){
            println "processing ${processingQueue.transcription.fileName}"
            processingQueueService.processTranscriptAsync(processingQueue)
        }


    }
}

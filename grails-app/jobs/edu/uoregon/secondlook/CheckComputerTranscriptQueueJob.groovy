package edu.uoregon.secondlook

/**
 */
class CheckComputerTranscriptQueueJob {

    def computerProcessingQueueService

    Integer MAX_QUEUE_SIZE = 5

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




        List<ComputerTranscript> transcriptionList = ComputerTranscript.findAllByStatusInList([TranscriptionStatus.RECEIVED],[max:MAX_QUEUE_SIZE-currentlyProcessing])
        for(ComputerTranscript transcription in transcriptionList){
            println "adding Transcript to process ${transcription.audioFile.fileName}"
            computerProcessingQueueService.submitTranscript(transcription.id)
        }





        List<ProcessingQueue> processingQueueList = ProcessingQueue.findAllByStatusInList([ProcessingStatus.DELIVERED],[max:MAX_QUEUE_SIZE-currentlyProcessing])
        Integer processesAdded = processingQueueList.size()
//        println "processes to process ${processesAdded}"

        for(ProcessingQueue processingQueue in processingQueueList){
            if(processingQueue.computerTranscript){
                println "processing ${processingQueue?.computerTranscript?.audioFile?.fileName}"
                computerProcessingQueueService.processTranscriptAsync(processingQueue.id)
            }
            else{
                processingQueue.delete(flush: true )
            }
        }


    }
}

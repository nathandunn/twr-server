import edu.uoregon.secondlook.ProcessingQueue
import edu.uoregon.secondlook.ProcessingStatus

class BootStrap {

    def processingQueueService

    def init = { servletContext ->

        ProcessingQueue.findAllByStatusNotInList([ProcessingStatus.FINISHED,ProcessingStatus.DELIVERED]).each{
            it.status= ProcessingStatus.PROCESSING
            it.save(flush: true)
            processingQueueService.processTranscriptAsync(it)
        }

    }
    def destroy = {
    }
}

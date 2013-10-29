import edu.uoregon.secondlook.Passage
import edu.uoregon.secondlook.PassageText
import edu.uoregon.secondlook.Patch
import edu.uoregon.secondlook.ProcessingQueue
import edu.uoregon.secondlook.ProcessingStatus

class BootStrap {

    def processingQueueService

    def init = { servletContext ->

        ProcessingQueue.findAllByStatusNotInList([ProcessingStatus.FINISHED, ProcessingStatus.DELIVERED]).each {
            it.status = ProcessingStatus.PROCESSING
            it.save(flush: true)
            processingQueueService.processTranscriptAsync(it)
        }

        if (!Patch.findByName("PassageIndex")) {

            Passage passage1 = new Passage(
                    name:"cut1"
                    ,text: PassageText.passage1
                    ,externalId: "cut1-external"
            ).save(insert:true)

            Passage passage2 = new Passage(
                    name:"cut2"
                    ,text: PassageText.passage2
                    ,externalId: "cut2-external"
            ).save(insert:true)

            Passage passage3 = new Passage(
                    name:"cut3"
                    ,text: PassageText.passage3
                    ,externalId: "cut3-external"
            ).save(insert:true)

            Patch patch = new Patch(
                    name: "PassageIndex"
                    , appliedDate: new Date()
            ).save(insert: true, flush: true, failOnError: true)
        }
    }
    def destroy = {
    }
}

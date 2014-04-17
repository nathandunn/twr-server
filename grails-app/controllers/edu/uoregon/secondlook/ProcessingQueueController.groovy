package edu.uoregon.secondlook

import org.springframework.dao.DataIntegrityViolationException

class ProcessingQueueController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def processingQueueService
    def computerProcessingQueueService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [processingQueueInstanceList: ProcessingQueue.list(params), processingQueueInstanceTotal: ProcessingQueue.count()]
    }

    def create() {
        [processingQueueInstance: new ProcessingQueue(params)]
    }

    def save() {
        def processingQueueInstance = new ProcessingQueue(params)
        if (!processingQueueInstance.save(flush: true)) {
            render(view: "create", model: [processingQueueInstance: processingQueueInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'processingQueue.label', default: 'ProcessingQueue'), processingQueueInstance.id])
        redirect(action: "show", id: processingQueueInstance.id)
    }

    def show(Long id) {
        def processingQueueInstance = ProcessingQueue.get(id)
        if (!processingQueueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'processingQueue.label', default: 'ProcessingQueue'), id])
            redirect(action: "list")
            return
        }

        [processingQueueInstance: processingQueueInstance]
    }

    def edit(Long id) {
        def processingQueueInstance = ProcessingQueue.get(id)
        if (!processingQueueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'processingQueue.label', default: 'ProcessingQueue'), id])
            redirect(action: "list")
            return
        }

        [processingQueueInstance: processingQueueInstance]
    }

    def update(Long id, Long version) {
        def processingQueueInstance = ProcessingQueue.get(id)
        if (!processingQueueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'processingQueue.label', default: 'ProcessingQueue'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (processingQueueInstance.version > version) {
                processingQueueInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'processingQueue.label', default: 'ProcessingQueue')] as Object[],
                          "Another user has updated this ProcessingQueue while you were editing")
                render(view: "edit", model: [processingQueueInstance: processingQueueInstance])
                return
            }
        }

        processingQueueInstance.properties = params

        if (!processingQueueInstance.save(flush: true)) {
            render(view: "edit", model: [processingQueueInstance: processingQueueInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'processingQueue.label', default: 'ProcessingQueue'), processingQueueInstance.id])
        redirect(action: "show", id: processingQueueInstance.id)
    }

    def delete(Long id) {
        def processingQueueInstance = ProcessingQueue.get(id)
        if (!processingQueueInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'processingQueue.label', default: 'ProcessingQueue'), id])
            redirect(action: "list")
            return
        }

        try {
            processingQueueInstance.computerTranscript.processingQueue = null
            processingQueueInstance.computerTranscript = null
//            processingQueueInstance.transcription.processingQueues = null
//            processingQueueInstance.computerTranscript = null

            processingQueueInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'processingQueue.label', default: 'ProcessingQueue'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'processingQueue.label', default: 'ProcessingQueue'), id])
            redirect(action: "show", id: id)
        }
    }


//    // REST POST
//    def sendTranscript(String externalStudentId,String externalPassageId,byte[] audio){
//        Passage passage = Passage.findByExternalId(externalPassageId)
//        Transcription transcription = new Transcription(
//                passage: passage
//                ,externalStudentId: externalStudentId
//                ,audioData: audio
//        ).save(insert: true,flush: true)
//        submitTranscript(transcription.id)
//
//        return transcription.id
//    }

    /**
     * @deprecated
     * @param id
     * @return
     */
    def submitTranscript(Long id) {
        processingQueueService.submitTranscript(id)
        redirect(action: "show", id: id,controller: "transcription")
    }

    def submitComputerTranscript(Long id) {
        computerProcessingQueueService.submitTranscript(id)
        redirect(action: "show", id: id,controller: "computerTranscript")
    }

}

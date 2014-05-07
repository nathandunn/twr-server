package edu.uoregon.secondlook

import org.springframework.dao.DataIntegrityViolationException

class PassageController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def computerProcessingQueueService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [passageInstanceList: Passage.list(params), passageInstanceTotal: Passage.count()]
    }

    def create() {
        [passageInstance: new Passage(params)]
    }

    def save() {
        def passageInstance = new Passage(params)
        if (!passageInstance.save(flush: true)) {
            render(view: "create", model: [passageInstance: passageInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'passage.label', default: 'Passage'), passageInstance.id])
        redirect(action: "show", id: passageInstance.id)
    }

    def show(Long id) {
        def passageInstance = Passage.get(id)
        if (!passageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'passage.label', default: 'Passage'), id])
            redirect(action: "list")
            return
        }

        Map<TranscriptionEngine,TranscriptionEngineStatistics> engineFloatMap = new TreeMap<>()

        TranscriptionEngine.all.each { transcriptionEngine ->
            TranscriptionEngineStatistics transcriptionEngineStatistics = computerProcessingQueueService.calculateTwrTranscriptionEngineAndPassage(transcriptionEngine,passageInstance)
            engineFloatMap.put(transcriptionEngine,transcriptionEngineStatistics)
        }

        [passageInstance: passageInstance,engineData:engineFloatMap]
    }

    def edit(Long id) {
        def passageInstance = Passage.get(id)
        if (!passageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'passage.label', default: 'Passage'), id])
            redirect(action: "list")
            return
        }

        [passageInstance: passageInstance]
    }

    def update(Long id, Long version) {
        def passageInstance = Passage.get(id)
        if (!passageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'passage.label', default: 'Passage'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (passageInstance.version > version) {
                passageInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'passage.label', default: 'Passage')] as Object[],
                          "Another user has updated this Passage while you were editing")
                render(view: "edit", model: [passageInstance: passageInstance])
                return
            }
        }

        passageInstance.properties = params

        if (!passageInstance.save(flush: true)) {
            render(view: "edit", model: [passageInstance: passageInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'passage.label', default: 'Passage'), passageInstance.id])
        redirect(action: "show", id: passageInstance.id)
    }

    def delete(Long id) {
        def passageInstance = Passage.get(id)
        if (!passageInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'passage.label', default: 'Passage'), id])
            redirect(action: "list")
            return
        }

        try {
            passageInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'passage.label', default: 'Passage'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'passage.label', default: 'Passage'), id])
            redirect(action: "show", id: id)
        }
    }

    // REST POST
    def sendPassage(String externalPassageId,String text){
        Passage.findOrSaveByExternalIdAndText(externalPassageId,text)
        render "OK"
    }
}

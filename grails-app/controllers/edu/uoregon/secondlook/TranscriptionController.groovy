package edu.uoregon.secondlook

import org.springframework.dao.DataIntegrityViolationException

class TranscriptionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [transcriptionInstanceList: Transcription.list(params), transcriptionInstanceTotal: Transcription.count()]
    }

    def create() {
        [transcriptionInstance: new Transcription(params)]
    }

    def save() {
        def transcriptionInstance = new Transcription(params)
        if (!transcriptionInstance.save(flush: true)) {
            render(view: "create", model: [transcriptionInstance: transcriptionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'transcription.label', default: 'Transcription'), transcriptionInstance.id])
        redirect(action: "show", id: transcriptionInstance.id)
    }

    def show(Long id) {
        def transcriptionInstance = Transcription.get(id)
        if (!transcriptionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transcription.label', default: 'Transcription'), id])
            redirect(action: "list")
            return
        }

        [transcriptionInstance: transcriptionInstance]
    }

    def edit(Long id) {
        def transcriptionInstance = Transcription.get(id)
        if (!transcriptionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transcription.label', default: 'Transcription'), id])
            redirect(action: "list")
            return
        }

        [transcriptionInstance: transcriptionInstance]
    }

    def update(Long id, Long version) {
        def transcriptionInstance = Transcription.get(id)
        if (!transcriptionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transcription.label', default: 'Transcription'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (transcriptionInstance.version > version) {
                transcriptionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'transcription.label', default: 'Transcription')] as Object[],
                          "Another user has updated this Transcription while you were editing")
                render(view: "edit", model: [transcriptionInstance: transcriptionInstance])
                return
            }
        }

        transcriptionInstance.properties = params

        if (!transcriptionInstance.save(flush: true)) {
            render(view: "edit", model: [transcriptionInstance: transcriptionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'transcription.label', default: 'Transcription'), transcriptionInstance.id])
        redirect(action: "show", id: transcriptionInstance.id)
    }

    def delete(Long id) {
        def transcriptionInstance = Transcription.get(id)
        if (!transcriptionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'transcription.label', default: 'Transcription'), id])
            redirect(action: "list")
            return
        }

        try {
            transcriptionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'transcription.label', default: 'Transcription'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'transcription.label', default: 'Transcription'), id])
            redirect(action: "show", id: id)
        }
    }
}

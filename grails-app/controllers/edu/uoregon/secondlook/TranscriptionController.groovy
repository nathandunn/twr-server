package edu.uoregon.secondlook

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.web.multipart.commons.CommonsMultipartFile

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

        CommonsMultipartFile uploadedFile = request.getFile('audioData')
        String fileName = uploadedFile.originalFilename

        Transcription transcriptionInstance = new Transcription(params)
        transcriptionInstance.fileName = fileName
        transcriptionInstance.requestDate = new Date()
        transcriptionInstance.status = TranscriptionStatus.RECEIVED
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

    private String fileName(String fileName) {
        if (fileName.endsWith(".wav")) {
            fileName = fileName.substring(0, fileName.length() - 4)
            fileName += ".timings.txt"
        }

        return fileName
    }

    def recalculateTwr(Long id) {
        Transcription transcription = Transcription.get(id)
        if (!transcription) {
            response.status = 404
            return
        }
        Passage passage = transcription.passage
        String passageText = passage.text
//        passageText = passageText.replaceAll("\n"," ")
        passageText = passageText.replaceAll("\\s{2,}"," ")
        Integer oldTwr = transcription.twr
        Integer twr = TWR.findTWR(passageText,transcription.transcript)
        transcription.twr = twr
        transcription.save(flush: true,insert: false)
        flash.message = "Recalculated TWR ${oldTwr} -> ${transcription.twr} for Transcrption ${transcription.fileName}"
        redirect(action: "show", id: transcription.id)
    }

    def downloadBinary(Integer id) {
        Transcription transcription = Transcription.get(id)
        if (!transcription) {
            response.status = 404
            return
        }
        if (transcription.transcript) {
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName(transcription.fileName+".wav"))
            render(file: transcription.audioData, contentType: "application/download", encoding: "UTF-8")
        }
    }

    def downloadTranscript(Integer id) {
        Transcription transcription = Transcription.get(id)
        if (!transcription) {
            response.status = 404
            return
        }
        if (transcription.transcript) {
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName(transcription.fileName))
            render(text: transcription.transcript, contentType: "application/download", encoding: "UTF-8")
        }
    }
}

package edu.uoregon.secondlook



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TranscriptionEngineController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TranscriptionEngine.list(params), model:[transcriptionEngineInstanceCount: TranscriptionEngine.count()]
    }

    def show(TranscriptionEngine transcriptionEngineInstance) {
        respond transcriptionEngineInstance
    }

    def create() {
        respond new TranscriptionEngine(params)
    }

    @Transactional
    def save(TranscriptionEngine transcriptionEngineInstance) {
        if (transcriptionEngineInstance == null) {
            notFound()
            return
        }

        if (transcriptionEngineInstance.hasErrors()) {
            respond transcriptionEngineInstance.errors, view:'create'
            return
        }

        transcriptionEngineInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'transcriptionEngine.label', default: 'TranscriptionEngine'), transcriptionEngineInstance.id])
                redirect transcriptionEngineInstance
            }
            '*' { respond transcriptionEngineInstance, [status: CREATED] }
        }
    }

    def edit(TranscriptionEngine transcriptionEngineInstance) {
        respond transcriptionEngineInstance
    }

    @Transactional
    def update(TranscriptionEngine transcriptionEngineInstance) {
        if (transcriptionEngineInstance == null) {
            notFound()
            return
        }

        if (transcriptionEngineInstance.hasErrors()) {
            respond transcriptionEngineInstance.errors, view:'edit'
            return
        }

        transcriptionEngineInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'TranscriptionEngine.label', default: 'TranscriptionEngine'), transcriptionEngineInstance.id])
                redirect transcriptionEngineInstance
            }
            '*'{ respond transcriptionEngineInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(TranscriptionEngine transcriptionEngineInstance) {

        if (transcriptionEngineInstance == null) {
            notFound()
            return
        }

        transcriptionEngineInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'TranscriptionEngine.label', default: 'TranscriptionEngine'), transcriptionEngineInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'transcriptionEngine.label', default: 'TranscriptionEngine'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

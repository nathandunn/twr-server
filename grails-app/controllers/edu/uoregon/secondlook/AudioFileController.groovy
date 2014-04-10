package edu.uoregon.secondlook



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AudioFileController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AudioFile.list(params), model:[audioFileInstanceCount: AudioFile.count()]
    }

    def show(AudioFile audioFileInstance) {
        respond audioFileInstance
    }

    def create() {
        respond new AudioFile(params)
    }

    @Transactional
    def save(AudioFile audioFileInstance) {
        if (audioFileInstance == null) {
            notFound()
            return
        }

        if (audioFileInstance.hasErrors()) {
            respond audioFileInstance.errors, view:'create'
            return
        }

        audioFileInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'audioFile.label', default: 'AudioFile'), audioFileInstance.id])
                redirect audioFileInstance
            }
            '*' { respond audioFileInstance, [status: CREATED] }
        }
    }

    def edit(AudioFile audioFileInstance) {
        respond audioFileInstance
    }

    @Transactional
    def update(AudioFile audioFileInstance) {
        if (audioFileInstance == null) {
            notFound()
            return
        }

        if (audioFileInstance.hasErrors()) {
            respond audioFileInstance.errors, view:'edit'
            return
        }

        audioFileInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'AudioFile.label', default: 'AudioFile'), audioFileInstance.id])
                redirect audioFileInstance
            }
            '*'{ respond audioFileInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(AudioFile audioFileInstance) {

        if (audioFileInstance == null) {
            notFound()
            return
        }

        audioFileInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'AudioFile.label', default: 'AudioFile'), audioFileInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'audioFile.label', default: 'AudioFile'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}

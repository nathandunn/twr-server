package edu.uoregon.secondlook



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class HumanTranscriptController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond HumanTranscript.list(params), model:[humanTranscriptInstanceCount: HumanTranscript.count()]
    }

    def show(HumanTranscript humanTranscriptInstance) {
        respond humanTranscriptInstance
    }

    def create() {
        respond new HumanTranscript(params)
    }

    @Transactional
    def save(HumanTranscript humanTranscriptInstance) {
        if (humanTranscriptInstance == null) {
            notFound()
            return
        }

        if (humanTranscriptInstance.hasErrors()) {
            respond humanTranscriptInstance.errors, view:'create'
            return
        }

        humanTranscriptInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'humanTranscript.label', default: 'HumanTranscript'), humanTranscriptInstance.id])
                redirect humanTranscriptInstance
            }
            '*' { respond humanTranscriptInstance, [status: CREATED] }
        }
    }

    def edit(HumanTranscript humanTranscriptInstance) {
        respond humanTranscriptInstance
    }

    @Transactional
    def update(HumanTranscript humanTranscriptInstance) {
        if (humanTranscriptInstance == null) {
            notFound()
            return
        }

        if (humanTranscriptInstance.hasErrors()) {
            respond humanTranscriptInstance.errors, view:'edit'
            return
        }

        humanTranscriptInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'HumanTranscript.label', default: 'HumanTranscript'), humanTranscriptInstance.id])
                redirect humanTranscriptInstance
            }
            '*'{ respond humanTranscriptInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(HumanTranscript humanTranscriptInstance) {

        if (humanTranscriptInstance == null) {
            notFound()
            return
        }

        humanTranscriptInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'HumanTranscript.label', default: 'HumanTranscript'), humanTranscriptInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'humanTranscript.label', default: 'HumanTranscript'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
package edu.uoregon.secondlook



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ComputerTranscriptController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ComputerTranscript.list(params), model:[computerTranscriptInstanceCount: ComputerTranscript.count()]
    }

    def show(ComputerTranscript computerTranscriptInstance) {
        respond computerTranscriptInstance
    }

    def create() {
        respond new ComputerTranscript(params)
    }

    @Transactional
    def save(ComputerTranscript computerTranscriptInstance) {
        if (computerTranscriptInstance == null) {
            notFound()
            return
        }

        if (computerTranscriptInstance.hasErrors()) {
            respond computerTranscriptInstance.errors, view:'create'
            return
        }

        computerTranscriptInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'computerTranscript.label', default: 'ComputerTranscript'), computerTranscriptInstance.id])
                redirect computerTranscriptInstance
            }
            '*' { respond computerTranscriptInstance, [status: CREATED] }
        }
    }

    def edit(ComputerTranscript computerTranscriptInstance) {
        respond computerTranscriptInstance
    }

    @Transactional
    def update(ComputerTranscript computerTranscriptInstance) {
        if (computerTranscriptInstance == null) {
            notFound()
            return
        }

        if (computerTranscriptInstance.hasErrors()) {
            respond computerTranscriptInstance.errors, view:'edit'
            return
        }

        computerTranscriptInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ComputerTranscript.label', default: 'ComputerTranscript'), computerTranscriptInstance.id])
                redirect computerTranscriptInstance
            }
            '*'{ respond computerTranscriptInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ComputerTranscript computerTranscriptInstance) {

        if (computerTranscriptInstance == null) {
            notFound()
            return
        }

        computerTranscriptInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ComputerTranscript.label', default: 'ComputerTranscript'), computerTranscriptInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'computerTranscript.label', default: 'ComputerTranscript'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def recalculateTwr(Long id) {
        ComputerTranscript transcription = ComputerTranscript.get(id)
        if (!transcription) {
            response.status = 404
            return
        }
        Passage passage = transcription.passage
        String passageText = passage.text
//        passageText = passageText.replaceAll("\n"," ")
        passageText = passageText.replaceAll("\\s{2,}", " ")
        Integer oldTwr = transcription.twr
        Integer twr = TWR.findTWR(passageText, transcription.transcript)
        transcription.twr = twr
        transcription.save(flush: true, insert: false)
        flash.message = "Recalculated TWR ${oldTwr} -> ${transcription.twr} for Transcrption ${transcription.audioFile.fileName}"
        redirect(action: "show", id: transcription.id)
    }
}

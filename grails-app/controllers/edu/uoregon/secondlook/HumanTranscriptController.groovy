package edu.uoregon.secondlook

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class HumanTranscriptController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def totalWordReadService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond HumanTranscript.list(params), model: [humanTranscriptInstanceCount: HumanTranscript.count()]
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
            respond humanTranscriptInstance.errors, view: 'create'
            return
        }

        if (humanTranscriptInstance.transcript) {
            humanTranscriptInstance.processedTranscript = totalWordReadService.processTranscript(humanTranscriptInstance.transcript)
        }

        humanTranscriptInstance.save flush: true

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
//    def update(HumanTranscript humanTranscriptInstance) {
    def update(Long id) {
        HumanTranscript humanTranscriptInstance = HumanTranscript.get(id)
        if (humanTranscriptInstance == null) {
            notFound()
            return
        }

        if (!params.originalFile.originalFilename) {
            params.originalFile = humanTranscriptInstance.originalFile
        }

        humanTranscriptInstance.properties = params

        if (humanTranscriptInstance.hasErrors()) {
            respond humanTranscriptInstance.errors, view: 'edit'
            return
        }

        if (humanTranscriptInstance.transcript) {
            humanTranscriptInstance.processedTranscript = totalWordReadService.processTranscript(humanTranscriptInstance.transcript)
        }

        humanTranscriptInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'HumanTranscript.label', default: 'HumanTranscript'), humanTranscriptInstance.id])
                redirect humanTranscriptInstance
            }
            '*' { respond humanTranscriptInstance, [status: OK] }
        }
    }

//    private String processTranscript(String inputTranscript) {
//        if(inputTranscript.contains("[")){
//
//            List<String> firstTokens = inputTranscript.tokenize("[")
//            List<String> lastTokens = new ArrayList<>()
//
//            firstTokens.each { token ->
//                if(token.contains("]")){
//                    lastTokens.add(token.substring(token.indexOf("]")+1))
//                }
//                else{
//                    lastTokens.add(token)
//                }
//            }
//            return (lastTokens.collect() as String).replaceAll(" +"," ").toUpperCase()
//        }
//        return inputTranscript.replaceAll(" +"," ").toUpperCase()
//    }

    @Transactional
    def delete(HumanTranscript humanTranscriptInstance) {

        if (humanTranscriptInstance == null) {
            notFound()
            return
        }

        humanTranscriptInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'HumanTranscript.label', default: 'HumanTranscript'), humanTranscriptInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'humanTranscript.label', default: 'HumanTranscript'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}

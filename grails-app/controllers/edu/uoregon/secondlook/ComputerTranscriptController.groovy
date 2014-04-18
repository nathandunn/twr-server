package edu.uoregon.secondlook

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ComputerTranscriptController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ComputerTranscript.list(params), model: [computerTranscriptInstanceCount: ComputerTranscript.count()]
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
            respond computerTranscriptInstance.errors, view: 'create'
            return
        }

        computerTranscriptInstance.save flush: true

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
            respond computerTranscriptInstance.errors, view: 'edit'
            return
        }

        computerTranscriptInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ComputerTranscript.label', default: 'ComputerTranscript'), computerTranscriptInstance.id])
                redirect computerTranscriptInstance
            }
            '*' { respond computerTranscriptInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(ComputerTranscript computerTranscriptInstance) {

        if (computerTranscriptInstance == null) {
            notFound()
            return
        }

        computerTranscriptInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ComputerTranscript.label', default: 'ComputerTranscript'), computerTranscriptInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'computerTranscript.label', default: 'ComputerTranscript'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def recalculateTwr(Long id) {
        ComputerTranscript transcription = ComputerTranscript.get(id)
        if (!transcription) {
            response.status = 404
            return
        }
        Passage passage = transcription.audioFile.passage
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

    private String fileName(String fileName) {
        if (fileName.endsWith(".wav")) {
            fileName = fileName.substring(0, fileName.length() - 4)
            fileName += ".timings.txt"
        }

        return fileName
    }

    def downloadTimingsFile(Long id) {
        ComputerTranscript computerTranscript = ComputerTranscript.get(id)
        if (!computerTranscript) {
            response.status = 404
            return
        }
        if (computerTranscript.transcript) {
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName(computerTranscript.audioFile.fileName))
            render(text: computerTranscript.transcript, contentType: "application/download", encoding: "UTF-8")
//            response.setHeader("Content-Disposition", "attachment; filename=computerTranscript-" + computerTranscript.audioFile.fileName+".timings.txt")
//            response.outputStream << computerTranscript.transcript
//            render(file: transcription.audioData, contentType: "application/download", encoding: "UTF-8")
        }
    }

    private String transcriptFileName(ComputerTranscript transcription) {
        String fileName = transcription.audioFile.fileName
        if (fileName.endsWith(".wav")) {
            fileName = fileName.substring(0, fileName.length() - 4)
            fileName += ".transcript.txt"
        }
        return fileName
    }

    private String generateTranscriptFile(ComputerTranscript transcription) {
        String returnString = ""
        if (transcription.transcript) {
            String timingsFile = transcription.transcript
            timingsFile.eachLine { line ->
                String[] cols = line.split(" ")
                if (cols.length == 5) {
                    returnString += cols[4].toUpperCase() + " "
                }
            }
        }
        return returnString.trim()
    }

    def downloadTranscripts(Integer id) {
        ComputerTranscript computerTranscript = ComputerTranscript.get(id)
        if (!computerTranscript || !computerTranscript.transcript) {
            response.status = 404
            return
        }
//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName(transcription.fileName))
        response.setHeader("Content-Disposition", "attachment; filename=" + transcriptFileName(computerTranscript))
        render(text: generateTranscriptFile(computerTranscript), contentType: "application/download", encoding: "UTF-8")
    }
}

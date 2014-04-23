package edu.uoregon.secondlook

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class AudioFileController {

    static allowedMethods = [save: "POST", update: "POST", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AudioFile.list(params), model: [audioFileInstanceCount: AudioFile.count()]
    }

    def show(AudioFile audioFileInstance) {
        List<Object> availableTranscripts = new ArrayList<>()

        availableTranscripts.addAll(audioFileInstance.computerTranscripts)
        availableTranscripts.addAll(audioFileInstance.humanTranscripts)

        Map<TranscriptionEngine, List<ComputerTranscript>> computerTranscriptMap = new HashMap<>()


        for (ComputerTranscript computerTranscript in audioFileInstance.computerTranscripts) {
            if (computerTranscript.transcriptionEngine) {

                if (computerTranscriptMap.containsKey(computerTranscript.transcriptionEngine)) {
                    List<ComputerTranscript> computerTranscriptList = computerTranscriptMap.get(computerTranscript.transcriptionEngine)
                    computerTranscriptList.add(computerTranscript)
                    computerTranscriptMap.put(computerTranscript.transcriptionEngine, computerTranscriptList)
                } else {
                    List<ComputerTranscript> computerTranscriptList = new ArrayList<>()
                    computerTranscriptList.add(computerTranscript)
                    computerTranscriptMap.put(computerTranscript.transcriptionEngine, computerTranscriptList)
                }
            }
        }

//        audioFileInstance.computerTranscripts.each {
//            if(it.transcript){
////                availableTranscripts.put(it.id,"Comp (${it.transcript.size()}):"+it?.transcriptionEngine?.name + " - " + it.requestDate)
//                availableTranscripts.put(it.id,"Comp (${it.transcript.size()}):"+it?.transcriptionEngine?.name + " - " + it.requestDate)
//            }
//        }

//        if(audioFileInstance.computerTranscripts && audioFileInstance.humanTranscripts){
//            availableTranscripts.put(null,"------------")
//        }

//        audioFileInstance.humanTranscripts.each {
//            availableTranscripts.put(it.id,"Human (${it.processedTranscript.size()}-${it.twr}):"+it.researcher+ " - " + it.processDate)
////            availableTranscripts.add(it.processedTranscript)
//        }

        respond audioFileInstance, model: [availableTranscripts: availableTranscripts, computerTranscripts: computerTranscriptMap]
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
            respond audioFileInstance.errors, view: 'create'
            return
        }

        audioFileInstance.save flush: true

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
    def update(Long id) {
        def audioFileInstance = AudioFile.get(id)
        if (audioFileInstance == null) {
            notFound()
            return
        }

//        println "params auidoData ${params.audioData?.size} ${params.audioData?.originalFilename}"

        if (!params.audioData.originalFilename) {
            params.audioData = audioFileInstance.audioData
        }

        audioFileInstance.properties = params

        if (audioFileInstance.hasErrors()) {
            respond audioFileInstance.errors, view: 'edit'
            return
        }


        audioFileInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'AudioFile.label', default: 'AudioFile'), audioFileInstance.id])
                redirect audioFileInstance
            }
            '*' { respond audioFileInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(AudioFile audioFileInstance) {

        if (audioFileInstance == null) {
            notFound()
            return
        }

        audioFileInstance.computerTranscripts.each {
            audioFileInstance.removeFromComputerTranscripts(it)
            audioFileInstance.save(flush: true)
            it.audioFile = null
            it.save(flush: true)
        }
//        audioFileInstance.humanTranscripts.del
//        audioFileInstance.computerTranscripts = nu
        audioFileInstance.passage = null
        audioFileInstance.save(flush: true)

        audioFileInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'AudioFile.label', default: 'AudioFile'), audioFileInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'audioFile.label', default: 'AudioFile'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

    def downloadBinary(Integer id) {
        AudioFile audioFile = AudioFile.get(id)
        if (!audioFile) {
            response.status = 404
            return
        }
        if (audioFile.audioData) {
            response.setHeader("Content-Disposition", "attachment; filename=" + audioFile.fileName)
            response.outputStream << audioFile.audioData
//            render(file: transcription.audioData, contentType: "application/download", encoding: "UTF-8")
        }
    }


}

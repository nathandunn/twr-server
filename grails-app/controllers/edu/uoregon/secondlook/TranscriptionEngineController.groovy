package edu.uoregon.secondlook



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TranscriptionEngineController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def computerProcessingQueueService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond TranscriptionEngine.list(params), model:[transcriptionEngineInstanceCount: TranscriptionEngine.count()]
    }

    def show(TranscriptionEngine transcriptionEngineInstance) {

//        Integer averageTwrDifference = computerProcessingQueueService.findTwrDifference(transcriptionEngineInstance)
		Integer averageErrorDifference = computerProcessingQueueService.findErrorDifference(transcriptionEngineInstance)

//                        ,averageHumanTwr:computerProcessingQueueService.calculateAverageHumanTwrForTranscriptionEngine(transcriptionEngineInstance)

        respond transcriptionEngineInstance,
                [model:[computerTranscripts:ComputerTranscript.findAllByTranscriptionEngine(transcriptionEngineInstance)
                        ,averageComputerTwr:computerProcessingQueueService.calculateAverageComputerTwrForTranscriptionEngine(transcriptionEngineInstance)
                        ,averageHumanTwr:computerProcessingQueueService.calculateAverageHumanTwrForTranscriptionEngine(transcriptionEngineInstance)
                        ,averageErrorDifference:averageErrorDifference]]
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

    @Transactional
    def createRemaining(Long id) {
        println "id: ${id}"

        TranscriptionEngine transcriptionEngine = TranscriptionEngine.findById(id)

        // find all audioFiles with no computer transcript using that transcriptEngine
        List<AudioFile> audioFileList = AudioFile.executeQuery("select distinct af from AudioFile af join af.computerTranscripts ct where ct.transcriptionEngine.id = :id ",[id:id])
        Set<AudioFile> uniqueSeq = new HashSet<>(audioFileList)
        println "with engine size ${uniqueSeq.size()}"

        List<AudioFile> allAudioFileList = AudioFile.all
        println "all size ${allAudioFileList.size()}"

        List<AudioFile> aList = allAudioFileList - uniqueSeq
        println "to process size ${aList.size()}"


        for(AudioFile audioFile in aList){
            println "processing ${audioFile.fileName}"

            ComputerTranscript transcription = new ComputerTranscript(
                    audioFile: audioFile
                    , requestDate: new Date()
                    , status: TranscriptionStatus.RECEIVED
                    ,transcriptionEngine: transcriptionEngine

            ).save(insert: true, flush: true, failOnError: true)

            computerProcessingQueueService.submitTranscript(transcription.id)
        }

        redirect(action: "index", controller: "processingQueue",params:[sort:"entryDate",order:"desc"])


//        List<AudioFile> audioFileList = new ArrayList<>()
//        AudioFile.all.each { audioFile ->
//            if(!audioFile.computerTranscripts){
//                audioFileList.add(audioFile)
//            }
//            else{
//                if(transcriptionEngine in )
//            }
//        }
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

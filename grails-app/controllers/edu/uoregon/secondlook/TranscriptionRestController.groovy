package edu.uoregon.secondlook

class TranscriptionRestController {

    def processingQueueService

    def submit(String fileName,byte[] audio,String passageId,String studentId)  {

//        static responseFormats = ['json', 'xml']

        Passage passage = Passage.findById(passageId as Long)

        if(!passage){
            return "Passage not found "
        }

        Transcription transcription = new Transcription(
                fileName: fileName
                ,audioData: audio
                ,passage: passage
                ,externalStudentId: studentId
        ).save(insert: true,flush: true,failOnError: true)

        processingQueueService.submitTranscript(transcription.id)

        return transcription.id

    }

    def status(Long id){
        Transcription transcription = Transcription.findById(id)

        if(transcription){
            return transcription.status.name()
        }
        else{
            return "Not found"
        }
    }
}

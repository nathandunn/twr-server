package edu.uoregon.secondlook

/**
 * Created by ndunn on 4/10/14.
 */
class DataStubber {

    def convertTranscripts(){
        TranscriptionEngine transcriptionEngine = TranscriptionEngine.findOrSaveByNameAndLookup(
                "default20140410"
                ,"s5-decode"
        )

        Transcription.findAllByFileNameLike('%wav').each { transcription ->
            AudioFile audioFile = new AudioFile(
                    audioData: transcription.audioData
                    ,fileName: transcription.fileName
                    ,externalStudentId: transcription.externalStudentId
                    ,callbackUrl: transcription.callbackUrl
                    ,note: transcription.note
            ).save(insert:true,failOnError: true)

            if(transcription.transcript){
                ComputerTranscript computerTranscript = new ComputerTranscript(
                       requestDate: transcription.requestDate
                        ,returnDate: transcription.returnDate
                        ,status: transcription.status
                        ,note: transcription.note
                        ,twr: transcription.twr
                        ,transcript: transcription.transcript
                ).save(insert:true,failOnError: true)
            }

            if(transcription.goldenTranscript){
                HumanTranscript humanTranscript = new HumanTranscript(
                        processDate: null
                        ,status: transcription.status
                        ,note: transcription.note
                        ,twr: transcription.twr
                        ,transcript: transcription.goldenTranscript
                        ,researcher:  "Carol"
                ).save(insert:true,failOnError: true)
            }
        }
    }
}

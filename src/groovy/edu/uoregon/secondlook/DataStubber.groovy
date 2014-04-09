package edu.uoregon.secondlook

/**
 * Created by ndunn on 4/9/14.
 */
class DataStubber {

    def moveData() {
        // assume one piece of data in each
        if (AudioFile.count > 0) return

        TranscriptionEngine transcriptionEngine = new TranscriptionEngine()

        Transcription.findAllByFileNameIlike("%wav").each { transcription ->


            AudioFile audioFile = new AudioFile(
                    audioData: transcription.audioData
                    ,fileName: transcription.fileName
                    ,externalStudentId: transcription.externalStudentId
                    ,callbackUrl: transcription.callbackUrl
                    ,note: transcription.note
                    ,passage: transcription.passage
            ).save(insert:true)

            if(transcription.transcript){
                ComputerTranscription computerTranscription = new ComputerTranscription(
                        requestDate: transcription.requestDate
                        ,returnDate: transcription.returnDate
                        ,status: transcription.status
                        ,transcriptErrors: transcription.transcriptErrors
                        ,note: transcription.note
                        ,audioFile: audioFile
                        ,twr: transcription.twr
                        ,transcript: transcription.transcript
                ).save(insert: true)

                audioFile.addToComputerTranscriptions(computerTranscription)
            }

            if(transcription.goldenTranscript){
                HumanTranscription humanTranscription = new HumanTranscription(
                        transcriber: transcription.goldenTranscript
                        ,note: transcription.note
                        ,audioFile: audioFile
                ).save(insert: true)
                audioFile.addToHumanTranscriptions()
            }

            audioFile.save(flush:true)
        }
    }
}

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

//        List<Transcription> transcriptionList = Transcription.findAllByFileNameLike('%wav')
        List<String> transcriptionNames = Transcription.executeQuery("select t.fileName from Transcription  t where t.fileName like '%wav'")
//        List<String> transcriptionNames = Transcription.executeQuery("select t.fileName from Transcription t ")
        println "# of transcripton names ${transcriptionNames.size()}"
        for(String fileName in transcriptionNames){
            Transcription transcription = Transcription.findByFileName(fileName)
            println "processing audio file ${transcription.fileName}"



            AudioFile audioFile = AudioFile.findByFileName(fileName)

            if(!audioFile){
                audioFile = new AudioFile(
                        audioData: transcription.audioData
                        ,fileName: transcription.fileName
                        ,externalStudentId: transcription.externalStudentId
                        ,callbackUrl: transcription.callbackUrl
                        ,note: transcription.note
                        ,passage: transcription.passage
                ).save(insert:true,failOnError: true,flush:true)
                transcription.passage.addToAudioFiles(audioFile)
            }

            if(transcription.transcript){
                println "creating computer transcript ${transcription.fileName}"
                ComputerTranscript computerTranscript = new ComputerTranscript(
                       requestDate: transcription.requestDate
                        ,returnDate: transcription.returnDate
                        ,status: transcription.status
                        ,note: transcription.note
                        ,twr: transcription.twr
                        ,transcript: transcription.transcript
                        ,audioFile: audioFile
                        ,transcriptionEngine: transcriptionEngine
                ).save(insert:true,failOnError: true,flush: true)
                audioFile.addToComputerTranscripts(computerTranscript)
            }

            if(transcription.goldenTranscript){
                println "creating human transcript ${transcription.fileName}"
                HumanTranscript humanTranscript = new HumanTranscript(
                        processDate: null
                        ,status: transcription.status
                        ,note: transcription.note
                        ,twr: transcription.twr
                        ,transcript: transcription.goldenTranscript
                        ,researcher:  "Carol"
                        ,audioFile: audioFile
                ).save(insert:true,failOnError: true,flush:true)
                audioFile.addToHumanTranscripts(humanTranscript)
            }

//            transcription.discard()
        }
    }
}

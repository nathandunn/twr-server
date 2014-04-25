package edu.uoregon.secondlook

class Passage {

    static constraints = {
    }

    static hasMany = [
            transcriptions: Transcription
            ,audioFiles: AudioFile
    ]

    static mapping = {
        text type: "text"
    }

    // ID is internal  long
    String name
    String text
    String externalId

    String getDisplay(){
        return externalId + " - " + name
    }

    def getNumberedText() {
        String returnPassage = ""
        if(text){
            text.split("\\s+").eachWithIndex{ token  , index ->
                returnPassage += token  + "<div class='numberShow'>${index+1}</div> "
            }
        }


        return returnPassage
    }

    String getWord(Integer wordNumber) {
        String[] tokens = text.split("\\s+")
        if(wordNumber>tokens.length){
            return ""
        }
        else{
            return tokens[wordNumber-1]
        }
    }

    Integer getComputerTranscriptCount(){
        int i = 0
        for(AudioFile audioFile in audioFiles){
            audioFile.computerTranscripts.each {
                ++i
            }
        }
        return i
    }

    Integer getHumanTranscriptCount(){
        int i = 0
        for(AudioFile audioFile in audioFiles){
            audioFile.humanTranscripts.each {
                ++i
            }
        }
        return i
    }
}

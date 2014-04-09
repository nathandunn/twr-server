package edu.uoregon.secondlook

class Passage {

    static constraints = {
    }

    static hasMany = [
            transcriptions: Transcription
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
            text.split(" ").eachWithIndex{ token  , index ->
                returnPassage += token  + "<div class='numberShow'> (${index+1})</div> "
            }
        }


        return returnPassage
    }

    String getWord(Integer wordNumber) {
        String[] tokens = text.split(" ")
        if(wordNumber>tokens.length){
            return ""
        }
        else{
            return tokens[wordNumber-1]
        }
    }
}

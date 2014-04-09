package edu.uoregon.secondlook

class TranscriptionEngine {

    static constraints = {
        creationDate nullable: true
    }

    String engineName
    String lookupString
    Date creationDate
}

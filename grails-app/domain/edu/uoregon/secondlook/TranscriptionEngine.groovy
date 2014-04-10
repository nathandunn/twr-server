package edu.uoregon.secondlook

class TranscriptionEngine {

    static constraints = {
        note nullable: true
    }

    static mapping = {
        note type: "text"
    }

    String name
    String lookup
    String note
}

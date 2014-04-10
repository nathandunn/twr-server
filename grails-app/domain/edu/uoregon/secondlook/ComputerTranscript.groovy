package edu.uoregon.secondlook

class ComputerTranscript {

    static constraints = {
        returnDate nullable: true
        transcript nullable: true
        status nullable: false
        twr nullable: true
        transcriptErrors nullable: true
        note nullable: true
        transcriptionEngine nullable: true
    }

    static mapping = {
        transcript type: "text"
        transcriptErrors type: "text"
        note type: "text"
    }

    Date requestDate
    Date returnDate
    TranscriptionStatus status
    String transcriptErrors
    String note
    TranscriptionEngine transcriptionEngine

    // results
    Integer twr
//    String timings
    String transcript // is the timings file
}

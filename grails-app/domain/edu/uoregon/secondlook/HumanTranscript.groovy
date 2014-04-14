package edu.uoregon.secondlook

class HumanTranscript {

    static constraints = {
        processDate nullable: true
        transcript nullable: true
        status nullable: false
        twr nullable: true
        transcriptErrors nullable: true
        passageErrors nullable: true
        note nullable: true
        researcher nullable: true
        audioFile nullable: false
        processedTranscript nullable: true
    }

    static mapping = {
        transcript type: "text"
        transcriptErrors type: "text"
        note type: "text"
    }

    Date processDate
    TranscriptionStatus status
    String transcriptErrors
    String passageErrors
    String note
    String researcher
    AudioFile audioFile

    // results
    Integer twr
//    String timings
    String transcript // is the human file
    String processedTranscript
}

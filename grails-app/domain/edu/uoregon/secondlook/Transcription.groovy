package edu.uoregon.secondlook

class Transcription {

    static constraints = {
        audioData(maxSize: 50000000)
        returnDate nullable: true
        transcript nullable: true
        status nullable: false
        twr nullable: true
        transcriptErrors nullable: true
        externalStudentId nullable: true
        callbackUrl nullable: true
        note nullable: true
        goldenTranscript nullable: true
    }

    static hasMany = [
            processingQueues: ProcessingQueue
    ]

    static mapping = {
        transcript type: "text"
        transcriptErrors type: "text"
        note type: "text"
        goldenTranscript type: "text"
    }

    byte[] audioData
    String fileName
    Date requestDate
    Date returnDate
    TranscriptionStatus status
    String externalStudentId
    String callbackUrl
    String transcriptErrors
    String note
    String goldenTranscript

    Passage passage


    // results
    Integer twr
//    String timings
    String transcript // is the timings file
}

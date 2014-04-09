package edu.uoregon.secondlook

/**
 * A Computer Transcription
 */
class ComputerTranscription {

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
        transcriptionEngine nullable: true
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

    // TODO: remove
    byte[] audioData
    String fileName
    Passage passage
    String externalStudentId
    String goldenTranscript

    // Keep the same
    Date requestDate
    Date returnDate
    TranscriptionStatus status
    String callbackUrl
    String transcriptErrors
    String note


    // TODO: convert
    AudioFile audioFile
    TranscriptionEngine transcriptionEngine


    // results
    Integer twr
//    String timings
    String transcript // is the timings file
}

package edu.uoregon.secondlook

/**
 * A Computer Transcription
 */
class ComputerTranscription {

    static constraints = {
        returnDate nullable: true
        transcript nullable: true
        status nullable: false
        twr nullable: true
        transcriptErrors nullable: true
        note nullable: true
        transcriptionEngine nullable: true
    }

    static hasMany = [
            processingQueues: ProcessingQueue
    ]

    static mapping = {
        transcript type: "text"
        transcriptErrors type: "text"
        note type: "text"
    }


    // Keep the same
    Date requestDate
    Date returnDate
    TranscriptionStatus status
//    String callbackUrl
    String transcriptErrors
    String note
    // results
    Integer twr
//    String timings
    String transcript // is the timings file


    // TODO: convert
    AudioFile audioFile
    TranscriptionEngine transcriptionEngine


}

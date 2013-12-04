package edu.uoregon.secondlook

class Transcription {

    static constraints = {
        audioData(maxSize: 50000000)
        returnDate nullable: true
        transcript nullable: true
        status nullable: false
        twr nullable: true
        externalStudentId nullable: true
    }

    static hasMany = [
            processingQueues: ProcessingQueue
    ]

    static mapping = {
        transcript type: "text"
    }

    byte[] audioData
    String fileName
    Date requestDate
    Date returnDate
    TranscriptionStatus status
    String externalStudentId
    String callbackUrl

    Passage passage


    // results
    Integer twr
//    String timings
    String transcript // is the timings file
}

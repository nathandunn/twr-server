package edu.uoregon.secondlook

class Transcription {

    static constraints = {
        audioData(maxSize: 50000000)
        returnDate nullable: true
        transcript nullable: true
        status nullable: false
    }

    static hasMany = [
            processingQueues: ProcessingQueue
    ]

    byte[] audioData
    String fileName
    Date requestDate
    Date returnDate
    TranscriptionStatus status

    String transcript
}

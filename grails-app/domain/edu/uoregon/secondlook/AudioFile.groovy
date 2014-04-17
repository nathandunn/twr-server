package edu.uoregon.secondlook

class AudioFile {

    static constraints = {
        audioData(maxSize: 50000000)
        externalStudentId nullable: true
        callbackUrl nullable: true
        note nullable: true
    }

    static hasMany = [
            processingQueues: ProcessingQueue
            ,humanTranscripts: HumanTranscript
            ,computerTranscripts: ComputerTranscript
    ]

    static mapping = {
        note type: "text"
        audioData lazy:true
    }

    byte[] audioData
    String fileName
    String externalStudentId
    String callbackUrl
    String note


    Passage passage
}

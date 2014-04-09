package edu.uoregon.secondlook

class AudioFile {

    static constraints = {
        audioData(maxSize: 50000000)
        externalStudentId nullable: true
        callbackUrl nullable: true
        note nullable: true
    }

    static hasMany = [
            computerTranscriptions: ComputerTranscription
            ,humanTranscriptions: HumanTranscription
    ]

    byte[] audioData
    String fileName
    String externalStudentId
    String callbackUrl
    String note
    Passage passage

}

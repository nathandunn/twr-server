package edu.uoregon.secondlook

class ProcessingQueue {

    static constraints = {
        transcription nullable: true
        computerTranscript nullable: true
    }

    static belongsTo = [
            computerTranscript: ComputerTranscript
    ]

    Transcription transcription
    ComputerTranscript computerTranscript
    Date entryDate
    ProcessingStatus status
}

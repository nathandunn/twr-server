package edu.uoregon.secondlook

class ProcessingQueue {

    static constraints = {
        transcription nullable: true
        computerTranscript nullable: true
    }

    Transcription transcription
    ComputerTranscript computerTranscript
    Date entryDate
    ProcessingStatus status
}

package edu.uoregon.secondlook

class ProcessingQueue {

    static constraints = {
        computerTranscription nullable: true
        transcription nullable: true
    }

    // TODO: remove
    Transcription transcription

    ComputerTranscription computerTranscription
    Date entryDate
    ProcessingStatus status
}

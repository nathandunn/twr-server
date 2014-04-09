package edu.uoregon.secondlook

class HumanTranscription {

    static constraints = {
        transcriberTranscription nullable: true
        transcriberPassageErrors nullable: true
        processedTranscription nullable: true
        processedPassageErrors nullable: true
        processDate nullable: false
        transcriber nullable: false
        audioFile nullable: false
    }

    static mapping = {
        transcriberTranscription type: "text"
        transcriberPassageErrors type: "text"
        processedTranscription type: "text"
        processedPassageErrors type: "text"
    }

    String transcriberTranscription
    String transcriberPassageErrors
    // what we use for a diff and export
    String processedTranscription
    String processedPassageErrors
    Date processDate


    String note
    // a new field
    String transcriber


    // TODO: convert
    AudioFile audioFile
}

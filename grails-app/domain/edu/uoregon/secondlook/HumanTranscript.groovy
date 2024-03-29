package edu.uoregon.secondlook

class HumanTranscript implements TranscriptDisplay{

    static constraints = {
//        originalFile(maxSize: 50000000)
        originalFile nullable: true
        processDate nullable: true
        transcript nullable: true
        status nullable: false
        twr nullable: true
        transcriptErrors nullable: true
        passageErrors nullable: true
        note nullable: true
        researcher nullable: true
        audioFile nullable: false
        processedTranscript nullable: true
    }

    static mapping = {
        transcript type: "text"
        originalFile type: "text"
        processedTranscript type: "text"
        transcriptErrors type: "text"
        note type: "text"
    }

    Date processDate
    TranscriptionStatus status
    String transcriptErrors
    String passageErrors
    String note
    String researcher
    AudioFile audioFile
    // this file // passage annotation
    String originalFile

    // results
    Integer twr
//    String timings
    // actual annotated transcript
    String transcript // is the human file
    String processedTranscript

    @Override
    String getDisplay() {
        String returnString = ""
        returnString += "Human ${twr}-${processedTranscript?.size()} - ${researcher} - ${processDate}"
        return returnString
    }
}

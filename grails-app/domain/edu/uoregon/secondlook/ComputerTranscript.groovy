package edu.uoregon.secondlook

class ComputerTranscript implements TranscriptDisplay{

    static constraints = {
        returnDate nullable: true
        transcript nullable: true
        status nullable: false
        twr nullable: true
        transcriptErrors nullable: true
        passageErrors nullable: true
        note nullable: true
        transcriptionEngine nullable: true
        audioFile nullable: false
        processingQueue nullable: true
        humanAnnotation nullable: true
        processingDirectory nullable: true
    }

    static mapping = {
        transcript type: "text"
        transcriptErrors type: "text"
        note type: "text"
        humanAnnotation type: "text"
    }

    static belongsTo = [
            audioFile: AudioFile
    ]

    Date requestDate
    Date returnDate
    TranscriptionStatus status
    String transcriptErrors
    String passageErrors
    String note
    TranscriptionEngine transcriptionEngine
//    AudioFile audioFile
    ProcessingQueue processingQueue
    String humanAnnotation

    // results
    Integer twr
//    String timings
    String transcript
    String processingDirectory // is the timings file

    @Override
    String getDisplay() {
        String returnString = ""

        returnString += "Comp ${twr}-${transcript?.size()} - ${transcriptionEngine?.name} - ${returnDate}"

        return returnString
    }
}

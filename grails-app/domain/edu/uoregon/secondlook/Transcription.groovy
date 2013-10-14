package edu.uoregon.secondlook

class Transcription {

    static constraints = {
        audioData(maxSize: 50000000)

    }

    byte[] audioData
    String fileName
    Date requestDate
    Date returnDate

    String transcript
}

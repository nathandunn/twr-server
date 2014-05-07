package edu.uoregon.secondlook

class TranscriptionEngine implements Comparable<TranscriptionEngine>{

    static constraints = {
        note nullable: true
    }

    static mapping = {
        note type: "text"
    }

    String name

    // what binary is called
    String lookup
    String note
    Boolean defaultEngine

    @Override
    int compareTo(TranscriptionEngine o) {
        return id.compareTo(o.id)
    }
}

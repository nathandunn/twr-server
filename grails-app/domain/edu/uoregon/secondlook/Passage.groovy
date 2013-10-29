package edu.uoregon.secondlook

class Passage {

    static constraints = {
    }

    static mapping = {
        text type: "text"
    }

    // ID is internal  long
    String name
    String text
    String externalId

}

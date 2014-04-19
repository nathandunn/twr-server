package edu.uoregon.secondlook

class ResearcherRole {
    String name

    static hasMany = [ users: ResearcherUser, permissions: String ]
    static belongsTo = ResearcherUser

    static constraints = {
        name(nullable: false, blank: false, unique: true)
    }
}

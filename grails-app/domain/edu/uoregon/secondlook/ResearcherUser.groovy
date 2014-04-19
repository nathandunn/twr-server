package edu.uoregon.secondlook

class ResearcherUser {
    String name
    String username
    String passwordHash
    
    static hasMany = [ roles: ResearcherRole, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false, unique: true,email: true)
    }
}

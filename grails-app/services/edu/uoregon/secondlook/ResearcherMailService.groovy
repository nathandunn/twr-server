package edu.uoregon.secondlook

import grails.transaction.Transactional

@Transactional
class ResearcherMailService {

    def mailService

    def sendPasswordReset(ResearcherUser researcher,String randomPassword) {
        mailService.sendMail{
            to "${researcher.username}"
            from "ndunn@uoregon.edu"
            subject "Your password has been reset on the stickleback server."
            html "Your password has been reset on the stickleback server to '${randomPassword}'. Please change"
        }
    }
}

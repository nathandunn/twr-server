package edu.uoregon.secondlook

import org.apache.shiro.crypto.hash.Sha256Hash

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ResearcherUserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    static navigation = [
            title: 'Researchers', action: 'index', order: 10,
    ]

    def researcherService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ResearcherUser.list(params), model:[researcherUserInstanceCount: ResearcherUser.count()]
    }

    def show(ResearcherUser researcherUserInstance) {
        respond researcherUserInstance
    }

    def create() {
        respond new ResearcherUser(params)
    }

    String isValidPassword(String password) {
        if (!password || password.length() < 6) {
            return "Password length must be greater than 6"
        }
        return null;
    }

//    @Transactional
//    def save(ResearcherUser researcherUserInstance) {
//        if (researcherUserInstance == null) {
//            notFound()
//            return
//        }
//
//        if (researcherUserInstance.hasErrors()) {
//            respond researcherUserInstance.errors, view:'create'
//            return
//        }
//
//        researcherUserInstance.save flush:true
//
//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.created.message', args: [message(code: 'researcherUser.label', default: 'ResearcherUser'), researcherUserInstance.id])
//                redirect researcherUserInstance
//            }
//            '*' { respond researcherUserInstance, [status: CREATED] }
//        }
//    }
    @Transactional
    def save(ResearcherUser researcherInstance) {
        if (researcherInstance == null) {
            notFound()
            return
        }

        String passwordErrorString = isValidPassword(params.password1)
        println "errorsTring ${passwordErrorString}"
        if (passwordErrorString == null) {
            if (params.password1.equals(params.password2)) {
                params.passwordHash = new Sha256Hash(params.password1).toHex()
                researcherInstance.passwordHash = params.passwordHash
            } else {
                researcherInstance.errors.rejectValue("passwordHash", "default.password.doesnotmatch", "Passwords do not match")
//                render(view: "create", model: [researcherInstance: researcherInstance])
//                respond researcherInstance.errors, view:'create'
                respond researcherInstance.errors, view: 'create'
                return
            }
        } else {
            researcherInstance.errors.rejectValue("passwordHash", "", passwordErrorString)
//            render(view: "create", model: [researcherInstance: researcherInstance])
//            respond researcherInstance.errors, view:'create'
            respond researcherInstance.errors, view: 'create'
            return
        }

//        researcherInstance.validate()

        if (researcherInstance.hasErrors()) {
            respond researcherInstance.errors, view: 'create'
            return
        }

        researcherInstance.save flush: true

        if(!researcherInstance.hasErrors()){
            ResearcherRole userRole = ResearcherRole.findByName(ResearcherService.ROLE_USER)
            if (researcherInstance) {
                researcherInstance.addToRoles(userRole)
            }
        }

//        request.withFormat {
//            form {
        flash.message = message(code: 'default.created.message', args: [message(code: 'researcherInstance.label', default: 'Researcher'), researcherInstance.name])
        redirect researcherInstance
//            }
//            '*' { respond researcherInstance, [status: CREATED] }
//        }
    }

    def edit(ResearcherUser researcherUserInstance) {
//        respond researcherUserInstance
        if (researcherService.isAdmin() || researcherService.currentUser.id == researcherUserInstance.id) {
            respond researcherUserInstance
        } else {
            render(view: "/unauthorized")
        }
    }

    @Transactional
    def update(ResearcherUser researcherInstance) {
        if (researcherInstance == null) {
            notFound()
            return
        }

        if (!researcherService.isAdmin() && researcherService.currentUser.id != researcherInstance.id) {
            render(view: "/unauthorized")
            return
        }

        if (researcherInstance.hasErrors()) {
            respond researcherInstance.errors, view: 'edit'
            return
        }
        if (params.password1) {

            String passwordErrorString = isValidPassword(params.password1)
            println "pasword Error string ? ${passwordErrorString}"
            if (passwordErrorString == null) {
                if (params.password1.equals(params.password2)) {
                    researcherInstance.passwordHash = new Sha256Hash(params.password1).toHex()
                } else {
                    researcherInstance.errors.rejectValue("passwordHash", "default.password.doesnotmatch", "Passwords do not match")
//                    render(view: "edit", model: [researcherInstance: researcherInstance])
                    respond researcherInstance.errors, view: 'edit'
                    return
                }
            } else {
                researcherInstance.errors.rejectValue("passwordHash", "", passwordErrorString)
//                flash.message = passwordErrorString
//                render(view: "edit", model: [researcherInstance: researcherInstance])
                respond researcherInstance.errors, view: 'edit'
                return
            }
        } else {
            params.passwordHash = researcherInstance.passwordHash
        }

        researcherInstance.properties = params


        researcherInstance.save flush: true

//        request.withFormat {
//            form {
        flash.message = message(code: 'default.updated.message', args: [message(code: 'Researcher.label', default: 'Researcher'), researcherInstance.name])
        redirect researcherInstance
//            }
//            '*'{ respond researcherInstance, [status: OK] }
//        }
    }

//    @Transactional
//    def update(ResearcherUser researcherUserInstance) {
//        if (researcherUserInstance == null) {
//            notFound()
//            return
//        }
//
//        if (researcherUserInstance.hasErrors()) {
//            respond researcherUserInstance.errors, view:'edit'
//            return
//        }
//
//        researcherUserInstance.save flush:true
//
//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.updated.message', args: [message(code: 'ResearcherUser.label', default: 'ResearcherUser'), researcherUserInstance.id])
//                redirect researcherUserInstance
//            }
//            '*'{ respond researcherUserInstance, [status: OK] }
//        }
//    }

    @Transactional
    def delete(ResearcherUser researcherUserInstance) {

        if (researcherUserInstance == null) {
            notFound()
            return
        }

        researcherUserInstance.delete flush:true

//        request.withFormat {
//            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ResearcherUser.label', default: 'ResearcherUser'), researcherUserInstance.id])
                redirect action:"index", method:"GET"
//            }
//            '*'{ render status: NO_CONTENT }
//        }
    }

    protected void notFound() {
//        request.withFormat {
//            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'researcherUser.label', default: 'ResearcherUser'), params.id])
                redirect action: "index", method: "GET"
//            }
//            '*'{ render status: NOT_FOUND }
//        }
    }
}

package edu.uoregon.secondlook

import cr.co.arquetipos.password.PasswordTools
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.crypto.hash.Sha256Hash
import org.apache.shiro.web.util.SavedRequest
import org.apache.shiro.web.util.WebUtils

class AuthController {
    def shiroSecurityManager
    def researcherMailService

    def index = { redirect(action: "login", params: params) }

    def login = {
        return [ username: params.username, rememberMe: (params.rememberMe != null), targetUri: params.targetUri ]
    }

    def signIn = {
        def authToken = new UsernamePasswordToken(params.username, params.password as String)

        // Support for "remember me"
        if (params.rememberMe) {
            authToken.rememberMe = true
        }
        
        // If a controller redirected to this page, redirect back
        // to it. Otherwise redirect to the root URI.
        def targetUri = params.targetUri ?: "/"
        
        // Handle requests saved by Shiro filters.
        SavedRequest savedRequest = WebUtils.getSavedRequest(request)
        if (savedRequest) {
            targetUri = savedRequest.requestURI - request.contextPath
            if (savedRequest.queryString) targetUri = targetUri + '?' + savedRequest.queryString
        }
        
        try{
            // Perform the actual login. An AuthenticationException
            // will be thrown if the username is unrecognised or the
            // password is incorrect.
            SecurityUtils.subject.login(authToken)

            log.info "Redirecting to '${targetUri}'."
            redirect(uri: targetUri)
        }
        catch (AuthenticationException ex){
            // Authentication failed, so display the appropriate message
            // on the login page.
            log.info "Authentication failure for user '${params.username}'."
            flash.message = message(code: "login.failed")

            // Keep the username and "remember me" setting so that the
            // user doesn't have to enter them again.
            def m = [ username: params.username ]
            if (params.rememberMe) {
                m["rememberMe"] = true
            }

            // Remember the target URI too.
            if (params.targetUri) {
                m["targetUri"] = params.targetUri
            }

            // Now redirect back to the login page.
            redirect(action: "login", params: m)
        }
    }

    def signOut = {
        // Log the user out of the application.
        SecurityUtils.subject?.logout()
        webRequest.getCurrentRequest().session = null

        // For now, redirect back to the home page.
        redirect(uri: "/")
    }

    def unauthorized = {
        render "You do not have permission to access this page."
    }

    def forgotPassword = {
        println "mapped forgotten password "
    }

    def resetPassword(String username) {
        log.warn "resetting forgotten password ${username}"
        ResearcherUser user = ResearcherUser.findByUsername(username)
        if (user) {
            // mail the password
            String randomPassword = PasswordTools.generateRandomPassword()
            user.passwordHash = new Sha256Hash(randomPassword).toHex()


            researcherMailService.sendPasswordReset(user, randomPassword)
            flash.message = "Password email sent to ${user.username}"
            params.username = username
            redirect(action: "login", params: params)
            return
        } else {
            flash.message = "Could not find user with that email [${user}]"
            params.username = username
            redirect(action: "forgotPassword", params: params)
            return
        }

    }
}

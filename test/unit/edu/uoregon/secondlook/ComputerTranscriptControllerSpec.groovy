package edu.uoregon.secondlook



import grails.test.mixin.*
import spock.lang.*

@TestFor(ComputerTranscriptController)
@Mock(ComputerTranscript)
class ComputerTranscriptControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.computerTranscriptInstanceList
            model.computerTranscriptInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.computerTranscriptInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            def computerTranscript = new ComputerTranscript()
            computerTranscript.validate()
            controller.save(computerTranscript)

        then:"The create view is rendered again with the correct model"
            model.computerTranscriptInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            computerTranscript = new ComputerTranscript(params)

            controller.save(computerTranscript)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/computerTranscript/show/1'
            controller.flash.message != null
            ComputerTranscript.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def computerTranscript = new ComputerTranscript(params)
            controller.show(computerTranscript)

        then:"A model is populated containing the domain instance"
            model.computerTranscriptInstance == computerTranscript
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def computerTranscript = new ComputerTranscript(params)
            controller.edit(computerTranscript)

        then:"A model is populated containing the domain instance"
            model.computerTranscriptInstance == computerTranscript
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/computerTranscript/index'
            flash.message != null


        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def computerTranscript = new ComputerTranscript()
            computerTranscript.validate()
            controller.update(computerTranscript)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.computerTranscriptInstance == computerTranscript

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            computerTranscript = new ComputerTranscript(params).save(flush: true)
            controller.update(computerTranscript)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/computerTranscript/show/$computerTranscript.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/computerTranscript/index'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def computerTranscript = new ComputerTranscript(params).save(flush: true)

        then:"It exists"
            ComputerTranscript.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(computerTranscript)

        then:"The instance is deleted"
            ComputerTranscript.count() == 0
            response.redirectedUrl == '/computerTranscript/index'
            flash.message != null
    }
}

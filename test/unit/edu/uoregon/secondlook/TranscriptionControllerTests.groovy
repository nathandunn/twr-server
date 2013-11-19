package edu.uoregon.secondlook

import grails.converters.JSON
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import org.junit.*
import grails.test.mixin.*
import static groovyx.net.http.ContentType.HTML


@TestFor(TranscriptionController)
@Mock(Transcription)
class TranscriptionControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/transcription/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.transcriptionInstanceList.size() == 0
        assert model.transcriptionInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.transcriptionInstance != null
    }

    void testSave() {
        controller.save()

        assert model.transcriptionInstance != null
        assert view == '/transcription/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/transcription/show/1'
        assert controller.flash.message != null
        assert Transcription.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/transcription/list'

        populateValidParams(params)
        def transcription = new Transcription(params)

        assert transcription.save() != null

        params.id = transcription.id

        def model = controller.show()

        assert model.transcriptionInstance == transcription
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/transcription/list'

        populateValidParams(params)
        def transcription = new Transcription(params)

        assert transcription.save() != null

        params.id = transcription.id

        def model = controller.edit()

        assert model.transcriptionInstance == transcription
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/transcription/list'

        response.reset()

        populateValidParams(params)
        def transcription = new Transcription(params)

        assert transcription.save() != null

        // test invalid parameters in update
        params.id = transcription.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/transcription/edit"
        assert model.transcriptionInstance != null

        transcription.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/transcription/show/$transcription.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        transcription.clearErrors()

        populateValidParams(params)
        params.id = transcription.id
        params.version = -1
        controller.update()

        assert view == "/transcription/edit"
        assert model.transcriptionInstance != null
        assert model.transcriptionInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/transcription/list'

        response.reset()

        populateValidParams(params)
        def transcription = new Transcription(params)

        assert transcription.save() != null
        assert Transcription.count() == 1

        params.id = transcription.id

        controller.delete()

        assert Transcription.count() == 0
        assert Transcription.get(transcription.id) == null
        assert response.redirectedUrl == '/transcription/list'
    }

    void testRestStatus(){
        RESTClient restClient = new RESTClient( 'http://localhost:8080/' )
        def resp = restClient.get( path : 'twr-server/transcription/status/1' )
        assert resp.status == 200
        assert resp.data == "SUBMITTED"


        RESTClient restClient2 = new RESTClient( 'http://localhost:8080/' )
        def resp2 = restClient2.get( path : 'twr-server/transcription/status/5123123' )
//        assert resp2.status == 404
        assert resp2.status == 200
        assert resp2.data == "NOT FOUND"
    }
}

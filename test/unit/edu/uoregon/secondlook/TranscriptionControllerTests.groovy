package edu.uoregon.secondlook

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.web.JSONBuilder
//import groovyx.net.http.HTTPBuilder
//import groovyx.net.http.Method
//import groovyx.net.http.RESTClient

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

    void testRestStatus() {
        RestBuilder rest = new RestBuilder()
        def resp = rest.get("http://localhost:8080/twr-server/transcription/status/1") {
        }
        assert resp.status == 200
        assert resp.text == "SUBMITTED"

        resp = rest.get("http://localhost:8080/twr-server/transcription/status/112321") {
        }
        assert resp.status == 200
        assert resp.text == "NOT FOUND"
    }

    void testRestSubmit() {
        RestBuilder rest = new RestBuilder()
        String url = "http://localhost:8080/twr-server/transcription/submit"
        File file = new File("./test/test-data/531-2531/decodable.wav")

        def builder = new JSONBuilder()
        JSON j = builder.build {
            fileName = "bob123.wav"
            passageId = "1"
            studentId = "asdfa"
            audioData = file.bytes
        }

        RestResponse resp = rest.post(url) {
//            json "{ fileName: 'bob123.wav', passageId: 1,studentId:'asdfa' }"
//            body j
            contentType "multipart/form-data"
//            contentType "multipart/form-data;charset=UTF-8;"
//            accept "text/json;text/plain;application/octet-stream"

//            body j

//            fileName = "bob123.wav"
            studentId = "asdfa"
            passageId = "1"
            audioData = file
        }

        assert resp.status == 200
//        println "return vlue ${resp.json.submitted}"
        assert resp.json.submitted!=null
//        assert resp.text == "NOT FOUND"

//        Map<String, Object> postData = new HashMap<>()
//        byte[] audioData = file.bytes
//        println "lenght of audio ${audioData.length}"
//        postData.put("fileName", "bob123.wav")
//        postData.put("audio", audioData)
//        postData.put("passageId", 1)
//        postData.put("studentId", "ASdf")

////        def resp = restClient.post( path : 'twr-server/transcription/submit'
//        def resp = restClient.post( 'twr-server/transcription/submit'){
//                contentType "multipart/form-data"
////                body: postData
//            fileName
//        }

    }

//
}

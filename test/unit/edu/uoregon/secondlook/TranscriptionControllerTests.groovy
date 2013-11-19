package edu.uoregon.secondlook



import org.junit.*
import grails.test.mixin.*

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

    void testRestController(){
        withHttp(uri: "http://www.google.com") {
            def html = get(path : '/search', query : [q:'Groovy'])
            assert html.HEAD.size() == 1
            assert html.BODY.size() == 1
        }

//        withHttp(uri: "http://localhost") {
//            def html = get(port: 8080,path : '/twr-server/transcription/status/1', query : [q:''])
//            assert html.HEAD.size() == 1
//            assert html.BODY.size() == 1
////            def response = get(path:"name")
//            println "response ${html}"
//        }
    }
}

package edu.uoregon.secondlook
import grails.test.mixin.TestFor
//import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(TranscriptionRestController)
class TranscriptionRestControllerSpec {

//    def setup() {
//    }
//
//    def cleanup() {
//    }

//    void "test something"() {
//    }

    void testRestController(){

        withRest(uri: "http://localhost:8080/twr-server/transcriptionRest/status/1") {
            def response = get(path:"name")
//            auth.basic model.username, model.password
        }


    }

}

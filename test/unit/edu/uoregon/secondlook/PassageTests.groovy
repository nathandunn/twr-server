package edu.uoregon.secondlook

import grails.plugins.rest.client.RestBuilder
import grails.test.mixin.TestFor
import org.json.simple.JSONObject

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Passage)
class PassageTests {

    void testRESTPassage() {
        def rest = new RestBuilder(connectTimeout:1000, readTimeout:20000, proxy:['localhost':8888])

        //            auth System.getProperty("artifactory.user"), System.getProperty("artifactory.pass")

        def resp = rest.get("http://grails.org/api/v1.0/plugin/acegi/")

//        def resp = rest.post("http://localhost:8080/twr-server/passage/sendPassage"){
//            contentType "json"
//            json '{ externalId: "test123", text: "A temporary test group" }'
//        }

        resp.json instanceof JSONObject

        println response.json

    }
}

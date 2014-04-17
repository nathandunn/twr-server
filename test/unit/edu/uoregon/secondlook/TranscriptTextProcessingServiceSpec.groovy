package edu.uoregon.secondlook
import grails.test.mixin.TestFor
import spock.lang.Specification
/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(TranscriptTextProcessingService)
class TranscriptTextProcessingServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "testBracketProcessing"() {
        assert ""==service.processTranscript("[asdf]")
        assert "b c"==service.processTranscript("b [asdf] c")
        assert "b c"==service.processTranscript("b   c")
        assert true==false

    }
}

package credible.software.event

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Event)
class EventSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test required values fail validation cannot be null"() {
        given:
            Event event =
                    new Event(guid: null,
                               url: null,
                           summary: null,
                           details: null,
                   thumbnailImgUrl: null,
                      detailImgUrl: null)
        when:
            event.validate()
        then:
            event.hasErrors() == true
            event.errors.fieldErrors.size() == 3
            event.errors.fieldErrors.collect{ it.field }.containsAll ('guid', 'summary', 'details')
    }

    void "test a proper event passes validation with no error"() {
        given:
        Event event =
                new Event(guid: "Some Guid",
                        url: "Some Url",
                        summary: "Some Summary",
                        details: "Some Details",
                        thumbnailImgUrl: "Some Thumbnail Url",
                        detailImgUrl: "Some Detail Image Url")
        when:
            event.validate()
        then:
            event.hasErrors() == false
    }

}

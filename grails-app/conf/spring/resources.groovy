import credible.software.event.marshallers.EventMarshallerJson
import credible.software.event.marshallers.PersonMarshallerJson
import grails.converters.JSON
import org.grails.web.converters.configuration.ObjectMarshallerRegisterer

beans = {
    personJsonMarshaller(ObjectMarshallerRegisterer) {
        marshaller = new PersonMarshallerJson()
        converterClass = JSON
        priority = 1
    }
    eventJsonMarshaller(ObjectMarshallerRegisterer) {
        marshaller = new EventMarshallerJson()
        converterClass = JSON
        priority = 1
    }
}

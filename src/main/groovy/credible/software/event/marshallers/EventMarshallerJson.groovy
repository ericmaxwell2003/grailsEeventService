package credible.software.event.marshallers

import credible.software.event.Event
import org.grails.web.converters.marshaller.ClosureObjectMarshaller

class EventMarshallerJson extends ClosureObjectMarshaller<Event> {

        public static marshal = { Event event ->
            def json = [:]
            json.guid = event.guid
            json.url = event.url
            json.summary = event.summary
            json.details = event.details
            json.eventDate = event.dateCreated
            // just using the dateCreated for simplicity. In a real example, there would be a separate date for when the event will take place.
            json
        }

        public EventMarshallerJson() {
            super(Event, marshal)
        }

}

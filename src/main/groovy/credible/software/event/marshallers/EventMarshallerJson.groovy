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
            json.thumbnailImgUrl = event.thumbnailImgUrl
            json.detailImgUrl = event.detailImgUrl
            json
        }

        public EventMarshallerJson() {
            super(Event, marshal)
        }

}

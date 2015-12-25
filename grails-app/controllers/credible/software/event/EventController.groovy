package credible.software.event

import grails.converters.JSON
import grails.transaction.Transactional

import javax.annotation.security.RolesAllowed

import static org.springframework.http.HttpStatus.CREATED


@RolesAllowed(["ROLE_CLIENT"])
@Transactional(readOnly = true)
class EventController {

    public static final int CURRENT_TOKEN_VERSION = 1;

    def index() {
        List<Event> events = []
        Date eventsAfterDate = parseEncodedTokenToGetAfterDate(params.syncToken)

        if(eventsAfterDate) {
            events = Event.findAll(sort:"dateCreated", order: 'desc') {
                dateCreated > eventsAfterDate
            }
        } else {
            events = Event.list(sort: "dateCreated", order: 'desc')
        }

        String newSyncToken = newSyncTokenForResults(events, params.syncToken)
        Map json = [
            syncToken: newSyncToken,
            events: events
        ]
        respond json
    }

    def show(String id) {
        Event event = Event.findByGuid(id)
        if(event) {
            respond event
        } else {
            response.status = 404
        }
    }

    @RolesAllowed(["ROLE_ADMIN"])
    @Transactional
    def save(Event event) {

        if (event == null) {
            transactionStatus.setRollbackOnly()
            response.status = 400
            def results = [errors: 'No event passed in to create.'
            ]
            render results as JSON
            return
        }

        if (event.hasErrors()) {
            transactionStatus.setRollbackOnly()
            response.status = 400
            def results = [errors: event.errors]
            render results as JSON
            return
        }

        event.save flush:true
        respond event, [status: CREATED]
    }

    private Date parseEncodedTokenToGetAfterDate(String encodedToken) {

        Date afterDate = null

        try {
            if(encodedToken != null) {
                String decodedToken = new String(encodedToken.decodeBase64())
                int version = decodedToken.split(/:/)[0] as int

                // Assuming we had different versions of the token, we may process it differently.
                // The point is that the version gives us some flexibility for future chagnes to the token
                // format.  For now, we'll only process it if the version matches our "current" version.
                if(version == CURRENT_TOKEN_VERSION) {
                    long dateTime = decodedToken.split(/:/)[1] as long
                    afterDate = new Date(dateTime)
                }
            }
        } catch (Exception e) {
            e.printStackTrace()
        }

        return afterDate
    }

    private String newSyncTokenForResults(List<Event> eventResults, String oldSyncToken) {
        if(eventResults?.size() == 0) {
            return oldSyncToken
        } else {
            int tokenVersion = CURRENT_TOKEN_VERSION
            long dateCreated = eventResults.get(0).dateCreated.getTime() // assumed to be ordered desc
            String plainToken = "${tokenVersion}:${dateCreated}"
            String encodedToken = plainToken.bytes.encodeBase64().toString()
            return encodedToken
        }
    }
}

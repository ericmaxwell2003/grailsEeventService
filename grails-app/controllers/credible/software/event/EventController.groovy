package credible.software.event

import grails.converters.JSON
import grails.transaction.Transactional

import javax.annotation.security.RolesAllowed

import static org.springframework.http.HttpStatus.*

@RolesAllowed(["ROLE_CLIENT"])
@Transactional(readOnly = true)
class EventController {

    def index() {
        params.max = 10
        respond Event.list(params)
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

}

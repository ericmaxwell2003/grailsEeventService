package credible.software.event

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import grails.util.Holders
import spock.lang.Specification
import grails.plugins.rest.client.RestBuilder

@Integration
@Rollback
class RegisterSpec extends Specification {
    String baseUrl
    RestBuilder rest
    String accessToken
    String clientId = 'event-resource-app-client-id';
    String clientSecret = 'event-resource-app-super-secret-client-secret';

    def setup() {
        baseUrl = Holders.grailsApplication.config.grails.serverURL
        rest = new RestBuilder()
        def response = rest.post("${baseUrl}/oauth/token?grant_type=client_credentials") {
            auth(clientId, clientSecret)
            accept("application/json")
        }

        response.status == 200
        assert 'read write' == response.json.scope
        assert 'bearer' == response.json.token_type
        accessToken = "Bearer $response.json.access_token"
    }

    void "successful signup"() {
        when:
        def response = rest.post("${baseUrl}/register") {
            header("Authorization", accessToken)
            contentType('application/json')
            json {
                fullName = 'Robert Risigliano'
                username = 'robertrisigliano'
                email = 'robertrisigliano@gmail.com'
                password = 'password'
            }
        }

        then:
        response.status == 201
        response.json.fullName == 'Robert Risigliano'
        response.json.username == 'robertrisigliano'
        response.json.email == 'robertrisigliano@gmail.com'
    }

    void "email address already taken"() {
        when:
        def response = rest.post("${baseUrl}/register") {
            header("Authorization", accessToken)
            contentType('application/json')
            json {
                fullName = 'Registration User'
                username = 'registration_user2'
                email = 'registration_user@donotreply.com'
                password = 'password'
            }
        }

        then:
        response.status == 400
        response.json.errors.first().message == "A user already exists with this email address"
    }

    void "username already taken"() {
        when:
        def response = rest.post("${baseUrl}/register") {
            header("Authorization", accessToken)
            contentType('application/json')
            json {
                fullName = 'Registration User'
                username = 'registration_user'
                email = 'registration_user@donotreply.com'
                password = 'password'

            }
        }

        then:
        response.status == 400
        response.json.errors.first().message == "A user already exists with this username"
    }

    void "invalid email address"() {
        when:
        def response = rest.post("${baseUrl}/register") {
            header("Authorization", accessToken)
            contentType('application/json')
            json {
                fullName = 'Registration User'
                username = 'registration_user'
                email = 'registration_user@donotreply.com'
                password = 'password'
            }
        }

        then:
        response.status == 400
        response.json.errors != null
    }

    void "unsuccessful signup"() {
        when:
        def response = rest.post("${baseUrl}/register") {
            header("Authorization", accessToken)
        }

        then:
        response.status == 400
        response.json.errors != null
    }
}

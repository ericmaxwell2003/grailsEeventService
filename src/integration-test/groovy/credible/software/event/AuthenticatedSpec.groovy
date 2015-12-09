package credible.software.event

import grails.util.Holders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import spock.lang.*
import grails.plugins.rest.client.RestBuilder

abstract class AuthenticatedSpec extends Specification {
    String baseUrl
    RestBuilder rest
    String accessToken
    String refreshToken

    def setup() {
        baseUrl = Holders.grailsApplication.config.grails.serverURL
        rest = new RestBuilder()
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>()
        params.add('username', 'registration_user')
        params.add('password', 'r3g1st3R')
        params.add('grant_type', 'password')
        params.add('scope', 'read write')
        params.add('client_secret', 'event-resource-app-super-secret-client-secret')
        params.add('client_id', 'event-resource-app-client-id')

        def response = rest.post("${baseUrl}/oauth/token") {
            auth(params.getFirst('client_id'), params.getFirst('client_secret'))
            accept("application/json")
            contentType("application/x-www-form-urlencoded")
            body(params)
        }

        response.status == 200
        assert 'read write' == response.json.scope
        assert 'bearer' == response.json.token_type
        accessToken = "Bearer $response.json.access_token"
        refreshToken = response.json.refresh_token
    }
}

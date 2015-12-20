package credible.software.event

import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional
import grails.util.Holders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@Transactional(readOnly = true)
class AuthenticateController {

    String baseUrl = Holders.grailsApplication.config.grails.serverURL
    RestBuilder rest

    def oauthToken(LoginCommand loginCommand) {

        RestBuilder rest = new RestBuilder()
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>()
        params.add('username', loginCommand.username)
        params.add('password', loginCommand.password)
        params.add('grant_type', 'password')
        params.add('scope', 'read write')
        params.add('client_secret', 'event-resource-app-super-secret-client-secret')
        params.add('client_id', 'event-resource-app-client-id')

        def oauthResp = rest.post("${baseUrl}/oauth/token") {
            auth(params.getFirst('client_id'), params.getFirst('client_secret'))
            accept("application/json")
            contentType("application/x-www-form-urlencoded")
            body(params)
        }

        response.status = oauthResp.status
        respond oauthResp.json
    }
}

class LoginCommand {
    String username
    String password

    static constraints = {
        username(blank: false)
        password(blank: false)
    }
}


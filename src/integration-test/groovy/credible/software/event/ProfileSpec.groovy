package credible.software.event

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback

@Integration
@Rollback
class ProfileSpec extends AuthenticatedSpec {

    void "get profile"() {
        when:
        def response = rest.get("${baseUrl}/profile") {
            header("Authorization", accessToken)
        }

        then:
        response.status == 200
    }
}

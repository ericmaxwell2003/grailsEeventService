import credible.software.event.Person
import credible.software.event.PersonSecurityRole
import credible.software.event.SecurityRole
import grails.util.Environment

class BootStrap {

    def init = { servletContext ->
        if (Environment.TEST == Environment.current) {
            PersonSecurityRole.executeUpdate("delete PersonSecurityRole")
            SecurityRole.executeUpdate("delete SecurityRole")
            Person.executeUpdate("delete Person")
        }

        def adminRole = SecurityRole.findByAuthority('ROLE_ADMIN') ?: new SecurityRole(authority: 'ROLE_ADMIN').save(failOnError: true)
        def userRole = SecurityRole.findByAuthority('ROLE_CLIENT') ?: new SecurityRole(authority: 'ROLE_CLIENT').save(failOnError: true)

        def user1 = Person.findByUsername('registration_user') ?: new Person(username: 'registration_user', email: 'registration_user@donotreply.com', password: 'r3g1st3R', fullName: 'Registration User').save(failOnError: true)
        if (!user1.securityRoles.contains(userRole)) {
            PersonSecurityRole.create user1, userRole, true
        }
        if (!user1.securityRoles.contains(adminRole)) {
            PersonSecurityRole.create user1, adminRole, true
        }
    }

    def destroy = {
    }
}

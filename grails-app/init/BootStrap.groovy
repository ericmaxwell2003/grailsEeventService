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

        def user1 = Person.findByUsername('admin') ?: new Person(username: 'admin', email: 'admin@donotreply.com', password: 'admin', fullName: 'Admin User').save(failOnError: true)
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

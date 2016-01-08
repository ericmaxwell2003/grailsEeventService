@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7' )

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

String token='fefdc9b7-701c-4417-a985-136f934ffca5'
int numEventsToCreate = 2

def http = new groovyx.net.http.HTTPBuilder( 'https://localhost:8443/events' )
http.headers.put('Authorization', "Bearer ${token}")

for(int i = 1; i <= numEventsToCreate; i++) {
    
    String id = UUID.randomUUID().toString()
    Map bodyMap = [
          guid : id,
          summary : "Event ${id[0..3]}",
          details : "Details ${id[0..5]}",
          eventDate : "2016-01-${i}T08:00:00Z"
        ]
            
    http.request( POST, JSON ) { req ->
        
        body = groovy.json.JsonOutput.toJson(bodyMap)
    
        response.success = { resp, json ->
            println resp.status
            println json
        }
        
        response.error = { resp, json ->
            println resp.status
            println json
        }
    }
}
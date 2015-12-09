class UrlMappings {

    static mappings = {

        '/register'(controller: 'register') {
            action = [POST: 'save']
            format = 'json'
        }

        '/login'(controller: 'authenticate') {
            action = [POST: 'oauthToken']
            format = 'json'
        }

        '/event'(resources: 'event', includes:['index', 'show', 'save']) {
            format = 'json'
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}

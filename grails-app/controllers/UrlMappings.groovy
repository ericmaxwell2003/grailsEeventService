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

        '/profile'(controller: 'profile') {
            action = [GET: 'index']
        }

        '/event'(resources: 'event', includes:['index', 'show', 'save']) {
            format = 'json'
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}

package com.softwareit.plugins

import com.softwareit.routes.addAuthRoutes
import com.softwareit.routes.addExampleRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
    routing {
        addExampleRoutes()
        addAuthRoutes()
    }
}
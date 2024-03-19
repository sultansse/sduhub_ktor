package com.softwareit.plugins

import com.softwareit.routes.students
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

//        // Static plugin. Try to access `/static/index.html`
//        static("/static") {
//            resources("static")
//        }
        randomStudent()
    }
    routing {
        get("/v1/students") {
            call.respond(
                HttpStatusCode.OK,
                students
            )
        }

    }
}

//fun Application.registerRandomStudentRoutes() {
//    routing {
//        randomStudent()
//    }
//}

fun Route.randomStudent() {
    get("/v1/student") {
        call.respond(
            HttpStatusCode.OK,
            students.random(),
        )
    }
}

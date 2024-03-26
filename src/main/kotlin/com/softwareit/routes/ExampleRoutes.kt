package com.softwareit.routes

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val fullname: String,
    val studentId: Int,
    val faculty: String,
)

fun Route.addExampleRoutes() {
    get("/student") {
        call.respond(
            HttpStatusCode.OK,
            students.random(),
        )
    }

    get("/students") {
        call.respond(
            HttpStatusCode.OK,
            students
        )
    }

    get("/html") {
        val client = HttpClient(CIO)
        val response = client.get(exampleUrl)
        call.respondText(response.bodyAsText())
        client.close()
    }
}

val students = listOf(
    Student("Michael Jordan", 200107106, "Physics"),
    Student("LeBron James", 200107105, "Mathematics"),
    Student("Stephen Curry", 200107102, "Biology"),
    Student("Kevin Durant", 200107143, "Chemistry"),
    Student("Kobe Bryant", 200107199, "Engineering"),
    Student("Shaquille O'Neal", 200107081, "History"),
    Student("Tim Duncan", 200107077, "English"),
    Student("Magic Johnson", 200107199, "Geology"),
    Student("Larry Bird", 200107094, "Economics")
)




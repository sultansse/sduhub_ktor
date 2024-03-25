package com.softwareit.plugins

import com.softwareit.routes.students
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking


@OptIn(InternalAPI::class)
fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

    routing {
        get("/v1/student") {
            call.respond(
                HttpStatusCode.OK,
                students.random(),
            )
        }
        post("/v1/student") {
            call.respond(
                HttpStatusCode.Accepted,
                students.random(),
            )
        }
    }
    routing {
        get("/v1/students") {
            call.respond(
                HttpStatusCode.OK,
                students
            )
        }
    }

    val credentials = Credentials(
        username = "200107106",
        password = "asdfgh890",
    )

    val urr = "https://ktor.io/docs/request.html#binary"
    val exampleUrl = "https://amedia.site/1542-monolog-farmacevta.html"


    routing {
        get("/v2/auth") {
            val client = HttpClient(CIO)
            val response = client.get(exampleUrl)
            call.respondText(response.bodyAsText())
            client.close()
        }
        post("/v2/auth") {
            val client = HttpClient(CIO)
            val response = client.get(exampleUrl)
            call.respondText(response.bodyAsText())
            client.close()
        }
    }

    val targetUrl = "https://oldmy.sdu.edu.kz/loginAuth.php"

    val myFormData = FormDataContent(Parameters.build {
        append("username", "200107106")
        append("password", "asdfgh890")
        append("modstring", "")
        append("LogIn", " Log in ")
    })

    routing {
        get("/v1/auth") {
            val client = HttpClient(Apache) {
                followRedirects = true
            }

            runBlocking {
                val response: HttpResponse = client.post("https://oldmy.sdu.edu.kz/loginAuth.php") {
                    body = FormDataContent(
                        Parameters.build {
                            append("username", "200107106")
                            append("password", "asdfgh890")
                            append("modstring", "")
                            append("LogIn", " Log in ")
                        }
                    )
                    headers {
                        append(HttpHeaders.ContentType, ContentType.MultiPart.FormData)
                        append(HttpHeaders.Cookie, "PHPSESSID=ssbu49jagvpfl6tn7t426reh37; uname=200107106")
                    }
                }

                println("Response status: ${response.status}")

                if (response.status.isSuccess()) {
                    // Get HTML content from the redirected response
                    val htmlContent = response.bodyAsText()
                    println("HTML content: $htmlContent")
                } else {
                    println("Error: ${response.status}")
                }
                call.respondText(response.bodyAsText())
            }


            client.close()
        }
    }
}

data class Credentials(
    val username: String,
    val password: String
)
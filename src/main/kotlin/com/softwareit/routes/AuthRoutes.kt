package com.softwareit.routes

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kotlinx.coroutines.runBlocking


const val exampleUrl = "https://amedia.site/1542-monolog-farmacevta.html"
const val targetUrl = "https://oldmy.sdu.edu.kz/loginAuth.php"

val myFormData = FormDataContent(Parameters.build {
    append("username", "200107106")
    append("password", "asdfgh890")
    append("modstring", "")
    append("LogIn", " Log in ")
})

@OptIn(InternalAPI::class)
fun Route.addAuthRoutes() {

    get("/v1/auth") {
        val client = HttpClient(Apache) {
            followRedirects = true
        }

        runBlocking {
            val response: HttpResponse = client.post(targetUrl) {
                contentType(ContentType.MultiPart.FormData)
                body = myFormData
            }

            call.respondText(response.bodyAsText())
        }

        client.close()
    }
}

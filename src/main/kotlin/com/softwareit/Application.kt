package com.softwareit

import com.softwareit.plugins.configureMonitoring
import com.softwareit.plugins.configureRouting
import com.softwareit.plugins.configureSecurity
import com.softwareit.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val baseUrl = "https://sduhub-ktor.onrender.com"
    val port = System.getenv().getOrDefault("PORT", "8080").toInt()

    embeddedServer(Netty, port, host = baseUrl, module = Application::module).start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}

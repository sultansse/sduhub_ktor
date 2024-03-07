package com.softwareit

import com.softwareit.plugins.configureMonitoring
import com.softwareit.plugins.configureRouting
import com.softwareit.plugins.configureSecurity
import com.softwareit.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
            .start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureMonitoring()
    configureSerialization()
    configureRouting()
}

package com.schoolarium.routing

import com.schoolarium.routing.routes.recordRoutes
import com.schoolarium.routing.routes.studentRoutes
import io.ktor.server.application.*
import io.ktor.server.response.respondText
import io.ktor.server.routing.*
import org.koin.ktor.ext.get

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        route("/api/students") {
            studentRoutes(get())
        }

        route("/api/records") {
            recordRoutes(get(), get())
        }
    }
}

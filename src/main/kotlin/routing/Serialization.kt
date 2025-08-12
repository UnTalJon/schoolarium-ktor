package com.schoolarium.routing

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
    install(StatusPages) {
        exception<BadRequestException> { call, cause ->
            val errorMessage = cause.cause?.message ?: "Solicitud inválida."

            call.respond(HttpStatusCode.BadRequest, mapOf("error" to errorMessage))
        }

        exception<RequestValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.reasons.joinToString())
        }
    }
}

package com.schoolarium.routing.request

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validate<CreateStudentRequest> { request ->
            if (request.name.isBlank())
                ValidationResult.Invalid("Name is required")
            else
                ValidationResult.Valid

        }
    }
}
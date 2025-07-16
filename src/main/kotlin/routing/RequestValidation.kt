package com.schoolarium.routing

import com.schoolarium.routing.request.StudentRequest
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configureRequestValidation() {
    install(RequestValidation) {
        validate<StudentRequest> { request ->
            if (request.identifier.length != 10)
                ValidationResult.Invalid("Student identifier should be 10 characters long")
            if (request.name.isBlank())
                ValidationResult.Invalid("Name is required")
            else
                ValidationResult.Valid

        }
    }
}
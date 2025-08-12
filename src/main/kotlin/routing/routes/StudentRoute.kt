package com.schoolarium.routing.routes

import com.schoolarium.data.ServiceResult
import com.schoolarium.domain.services.StudentService
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.studentRoutes(studentService: StudentService) {
    get {
        studentService.findAll()
            .onSuccess { call.respond(HttpStatusCode.OK, it) }
            .onFailure { call.respond(HttpStatusCode.BadRequest, it) }
    }

    get("/findById/{id}") {
        try {
            val id = call.parameters["id"] ?: throw Exception("ID is required")

            val student = studentService.findById(id)
            call.respond(HttpStatusCode.Found, student)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, e.message ?: "Unknown error")
        }

    }

    post {
        try {
            val response = studentService.create(call)

            call.respond(HttpStatusCode.Created, response)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
        }
    }

    patch("/{id}") {
        try {
            val response = studentService.update(call)

            call.respond(HttpStatusCode.OK, response)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, mapOf("message" to e.message))
        }
    }

    delete("/{id}") {
        val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")

        when (val result = studentService.delete(id)) {
            is ServiceResult.Success -> call.respond(HttpStatusCode.Accepted, studentService.findById(id))
            is ServiceResult.NotFound -> call.respond(
                HttpStatusCode.NotFound,
                mapOf("message" to result.message)
            )

            is ServiceResult.Error -> call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("message" to result.message)
            )
        }
    }
}
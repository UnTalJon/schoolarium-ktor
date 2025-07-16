package com.schoolarium.routing.routes

import com.schoolarium.infrastructure.services.StudentService
import com.schoolarium.routing.request.StudentRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.log
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.application
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import java.util.UUID

fun Route.studentRoutes(studentService: StudentService) {
    get {
        val students = studentService.findAll()
        call.respond(HttpStatusCode.OK, students)
    }

    get("/findById/{id}") {
        val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
        val student = studentService.findById(UUID.fromString(id))
            ?: return@get call.respond(HttpStatusCode.NotFound)

        call.respond(HttpStatusCode.Found, student)
    }

    get("/findByIdentifier/{identifier}") {
        val id = call.parameters["identifier"] ?: return@get call.respond(HttpStatusCode.BadRequest)
        val student = studentService.findByIdentifier(id)
            ?: return@get call.respond(HttpStatusCode.NotFound)
        call.respond(HttpStatusCode.Found, student)
    }

    post {
        try {
            val request = call.receive<StudentRequest>()
            val response = studentService.save(request)
            call.respond(HttpStatusCode.Created, response)
        } catch (e: Exception) {
            // Este catch es opcional, ya que StatusPages se encargará.
            // Es útil para depuración.
            application.log.error("Error en la ruta /register", e)
            throw e
        }
    }
}
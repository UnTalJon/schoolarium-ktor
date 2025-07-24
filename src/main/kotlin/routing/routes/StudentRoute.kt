package com.schoolarium.routing.routes

import com.schoolarium.data.enums.RecordType
import com.schoolarium.domain.services.StudentService
import com.schoolarium.routing.request.StudentRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.studentRoutes(studentService: StudentService) {

    get {
        val students = studentService.findAll()
        call.respond(HttpStatusCode.OK, students)
    }

    get("/findById/{id}") {
        val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
        val student = studentService.findById(id)
            ?: return@get call.respond(HttpStatusCode.NotFound)

        call.respond(HttpStatusCode.Found, student)
    }

    get("/findByIdentifier/{identifier}") {
        val id = call.parameters["identifier"] ?: return@get call.respond(HttpStatusCode.BadRequest)
        val student = studentService.findByIdentifier(id)
            ?: return@get call.respond(HttpStatusCode.NotFound).also {
                call.application.environment.log.info("Ingreso fallido. El alumno no existe o no se encontró<UNK>")
            }
        call.respond(HttpStatusCode.OK, student)
        call.application.log.info("Ingreso registrado. Alumno: ${student.fullname()}, ID: ${student.id}")
    }

    post {
        try {
            val request = call.receive<StudentRequest>()
            val response = studentService.save(request)
            call.respond(HttpStatusCode.Created, response)
        } catch (e: Exception) {
            // Este catch es opcional, ya que StatusPages se encargará.
            // Es útil para depuración.
            application.log.error("Error en el método /post", e)
            throw e
        }
    }

    patch("/{id}") {
        val id = call.parameters["id"] ?: return@patch call.respond(HttpStatusCode.BadRequest)
        val student = call.receive<StudentRequest>()
        val updatedStudent = studentService.update(id, student)
            ?: return@patch call.respond(HttpStatusCode.NotModified)

        call.respond(HttpStatusCode.OK, updatedStudent)
    }
}
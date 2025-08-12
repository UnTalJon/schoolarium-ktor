package com.schoolarium.routing.routes

import com.schoolarium.data.enums.RecordType
import com.schoolarium.data.enums.StatusType
import com.schoolarium.domain.services.RecordService
import com.schoolarium.domain.services.StudentService
import com.schoolarium.routing.request.RecordRequest
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.recordRoutes(
    studentService: StudentService,
    recordService: RecordService
) {
    get("/{id}/{recordType}") {
        val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
        val recordTypeAsText = call.parameters["recordType"] ?: return@get call.respond(HttpStatusCode.BadRequest)

        try {
            val student = studentService.findById(id)
            val recordType = RecordType.valueOf(recordTypeAsText)

            val recordRequest = RecordRequest(
                studentId = student.id,
                type = recordType,
                status = StatusType.SUCCESS,
            )

            val record = recordService.create(recordRequest)

            call.respond(HttpStatusCode.Created, record)
        } catch (e: IllegalArgumentException) {
            val recordRequest = RecordRequest(
                studentId = id,
                type = RecordType.CHECK_IN,
                status = StatusType.ERROR,
            )

            recordService.create(recordRequest)
            return@get call.respond(HttpStatusCode.BadRequest)
        }
    }

    get("/history/{id}") {
        val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
        val records = recordService.findHistoryById(id)

        call.respond(HttpStatusCode.OK, records)
    }
}
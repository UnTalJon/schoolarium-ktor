package com.schoolarium.domain.mappers


import com.schoolarium.data.models.Record
import com.schoolarium.routing.response.RecordResponse

fun Record.toResponse() = RecordResponse(
    id = this.id.value,
    studentId = this.studentId,
    type = this.type,
    status = this.status,
    createdAt = this.createdAt
)
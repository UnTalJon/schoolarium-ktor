package com.schoolarium.routing.response

import com.schoolarium.data.enums.RecordType
import com.schoolarium.data.enums.StatusType
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class RecordResponse(
    val id: Int,
    val studentId: String,
    val type: RecordType,
    val status: StatusType,
    val createdAt: LocalDateTime,
)
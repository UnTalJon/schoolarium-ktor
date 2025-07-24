package com.schoolarium.routing.request

import com.schoolarium.data.enums.RecordType
import com.schoolarium.data.enums.StatusType
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class RecordRequest(
    val studentId: String,
    val type: RecordType,
    val status: StatusType,
)

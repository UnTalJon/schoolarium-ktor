package com.schoolarium.data.models

import com.schoolarium.data.enums.RecordType
import com.schoolarium.data.enums.StatusType
import com.schoolarium.util.UUIDSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Record(
    val id: Long,
    val studentId: @Serializable(with = UUIDSerializer::class) UUID,
    val type: RecordType,
    val status: StatusType,
    val createdAt: Instant,
)
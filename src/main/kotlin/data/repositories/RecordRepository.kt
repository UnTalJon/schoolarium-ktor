package com.schoolarium.data.repositories

import com.schoolarium.data.models.Record
import com.schoolarium.routing.request.RecordRequest
import java.util.UUID

interface RecordRepository {
    suspend fun save(record: RecordRequest): Record
    suspend fun findHistoryById(id: UUID): List<Record>
}
package com.schoolarium.data.repositories

import com.schoolarium.data.models.Record
import com.schoolarium.routing.request.RecordRequest

interface RecordRepository {
    suspend fun create(record: RecordRequest): Record
    suspend fun findHistoryById(id: String): List<Record>
}
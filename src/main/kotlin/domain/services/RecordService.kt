package com.schoolarium.domain.services

import com.schoolarium.data.repositories.RecordRepository
import com.schoolarium.routing.request.RecordRequest
import java.util.UUID

class RecordService(
    private val recordRepository: RecordRepository
) {
    suspend fun save(record: RecordRequest) = recordRepository.save(record)
    suspend fun findHistoryById(id: String) = recordRepository.findHistoryById(UUID.fromString(id))
}
package com.schoolarium.domain.services

import com.schoolarium.data.repositories.RecordRepository
import com.schoolarium.domain.mappers.toResponse
import com.schoolarium.routing.request.RecordRequest
import com.schoolarium.routing.response.RecordResponse

class RecordService(
    private val recordRepository: RecordRepository
) {
    suspend fun create(record: RecordRequest): RecordResponse = recordRepository.create(record).toResponse()
    suspend fun findHistoryById(id: String): List<RecordResponse> =
        recordRepository.findHistoryById(id).map { it.toResponse() }
}
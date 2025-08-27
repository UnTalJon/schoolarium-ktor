package com.schoolarium.domain.repositories

import com.schoolarium.data.models.Record
import com.schoolarium.data.repositories.RecordRepository
import com.schoolarium.routing.request.RecordRequest
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class RecordRepositoryImp : RecordRepository {
    override suspend fun create(record: RecordRequest): Record = transaction {
        Record.new {
            studentId = record.studentId
            type = record.type
            status = record.status
        }
    }

    override suspend fun findHistoryById(id: String): List<Record> = transaction {
        Record.all()
            .filter { it.studentId == id }
    }
}

package com.schoolarium.domain.repositories

import com.schoolarium.data.models.Record
import com.schoolarium.data.repositories.RecordRepository
import com.schoolarium.database.tables.RecordTable
import com.schoolarium.routing.request.RecordRequest
import com.schoolarium.util.dbQuery
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import java.util.UUID

class RecordRepositoryImp : RecordRepository {
    override suspend fun save(record: RecordRequest): Record = dbQuery {
        try {
            val resultRow = RecordTable.insert {
                it[studentId] = UUID.fromString(record.studentId)
                it[type] = record.type
                it[status] = record.status
            }

            Record(
                id = resultRow[RecordTable.id].value,
                studentId = resultRow[RecordTable.studentId],
                type = resultRow[RecordTable.type],
                status = resultRow[RecordTable.status],
                createdAt = resultRow[RecordTable.createdAt],
            )
        } catch (e: Exception) {
            throw IllegalStateException("Failed to insert record due to error: ${e.message}")
        }
    }

    override suspend fun findHistoryById(id: UUID): List<Record> = dbQuery {
        RecordTable.selectAll()
            .where(RecordTable.studentId eq id)
            .map {
                Record(
                    id = it[RecordTable.id].value,
                    studentId = it[RecordTable.studentId],
                    type = it[RecordTable.type],
                    status = it[RecordTable.status],
                    createdAt = it[RecordTable.createdAt],
                )
            }
    }
}
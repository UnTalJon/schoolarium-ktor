package com.schoolarium.data.models

import com.schoolarium.database.tables.RecordTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass


class Record(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Record>(RecordTable)


    var studentId by RecordTable.studentId
    var type by RecordTable.type
    var status by RecordTable.status
    var createdAt by RecordTable.createdAt
}
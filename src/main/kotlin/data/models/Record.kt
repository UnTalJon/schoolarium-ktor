package com.schoolarium.data.models

import com.schoolarium.database.tables.RecordTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Record(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Record>(RecordTable)


    var studentId by RecordTable.studentId
    var type by RecordTable.type
    var status by RecordTable.status
    var createdAt by RecordTable.createdAt
}
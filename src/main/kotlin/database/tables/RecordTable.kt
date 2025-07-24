package com.schoolarium.database.tables

import com.schoolarium.data.enums.RecordType
import com.schoolarium.data.enums.StatusType
import kotlinx.datetime.Clock
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object RecordTable : LongIdTable("records") {
    val studentId = uuid("student_id")
    val type = enumeration("type", RecordType::class)
    val status = enumeration("status", StatusType::class)
    val createdAt = timestamp("created_at").clientDefault { Clock.System.now() }

    init {
        foreignKey(studentId to StudentTable.id)
    }
}
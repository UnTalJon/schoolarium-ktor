package com.schoolarium.database.tables

import com.schoolarium.data.enums.RecordType
import com.schoolarium.data.enums.StatusType
import com.schoolarium.database.STUDENT_IDENTIFIER_LENGTH
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime


object RecordTable : IntIdTable("records") {
    val studentId = varchar("student_id", STUDENT_IDENTIFIER_LENGTH)
    val type = enumeration("type", RecordType::class)
    val status = enumeration("status", StatusType::class)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    init {
        foreignKey(studentId to StudentTable.id)
    }
}
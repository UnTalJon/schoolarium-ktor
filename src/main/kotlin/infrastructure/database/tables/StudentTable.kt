package com.schoolarium.infrastructure.database.tables

import com.schoolarium.infrastructure.database.MAX_VARCHAR_LENGTH
import com.schoolarium.infrastructure.database.STUDENT_IDENTIFIER_LENGTH
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.charLength

object StudentTable : UUIDTable("students") {
    val identifier = varchar("identifier", STUDENT_IDENTIFIER_LENGTH)
        .uniqueIndex()
        .check { it.charLength() eq STUDENT_IDENTIFIER_LENGTH }
    val name = varchar("name", MAX_VARCHAR_LENGTH)
    val firstSurname = varchar("first_surname", MAX_VARCHAR_LENGTH).nullable()
    val secondSurname = varchar("second_surname", MAX_VARCHAR_LENGTH).nullable()

}
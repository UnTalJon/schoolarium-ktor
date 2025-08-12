package com.schoolarium.database.tables

import com.schoolarium.database.MAX_VARCHAR_LENGTH
import com.schoolarium.database.MEDIUM_VARCHAR_LENGTH
import com.schoolarium.database.SHORT_VARCHAR_LENGTH
import com.schoolarium.database.STUDENT_IDENTIFIER_LENGTH
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.charLength

object StudentTable : IdTable<String>("students") {
    override val id = varchar("id", STUDENT_IDENTIFIER_LENGTH)
        .check { it.charLength() eq STUDENT_IDENTIFIER_LENGTH }
        .entityId()
    val name = varchar("name", MEDIUM_VARCHAR_LENGTH)
    val firstSurname = varchar("first_surname", MEDIUM_VARCHAR_LENGTH)
    val secondSurname = varchar("second_surname", MEDIUM_VARCHAR_LENGTH).nullable()
    val group = varchar("group", SHORT_VARCHAR_LENGTH).nullable()
    val grade = varchar("grade", SHORT_VARCHAR_LENGTH).nullable()
    val major = varchar("major", MEDIUM_VARCHAR_LENGTH).nullable()
    val profilePicturePath = varchar("profile_picture_path", MAX_VARCHAR_LENGTH).nullable()

    override val primaryKey = PrimaryKey(id)
}
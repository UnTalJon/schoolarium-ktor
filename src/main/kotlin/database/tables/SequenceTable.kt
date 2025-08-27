package com.schoolarium.database.tables

import com.schoolarium.database.STUDENT_IDENTIFIER_LENGTH
import org.jetbrains.exposed.v1.core.Table

object SequenceTable : Table("sequences") {
    val prefix = varchar("prefix", STUDENT_IDENTIFIER_LENGTH).uniqueIndex()
    val lastSequence = integer("last_sequence").default(0)

    override val primaryKey = PrimaryKey(prefix)
}
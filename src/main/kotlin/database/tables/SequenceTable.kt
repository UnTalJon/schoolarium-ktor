package com.schoolarium.database.tables

import org.jetbrains.exposed.sql.Table

object SequenceTable: Table("sequences") {
    val prefix = varchar("prefix", 9)
    val lastSequence = integer("last_sequence").default(0)

    override val primaryKey = PrimaryKey(prefix)
}
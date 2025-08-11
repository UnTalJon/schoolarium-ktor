package com.schoolarium.domain

import com.schoolarium.database.STUDENT_IDENTIFIER_LENGTH
import com.schoolarium.database.tables.SequenceTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.upsert
import java.time.LocalDateTime

class IdGenerator(app: Application) {

    val config = app.environment.config

    fun generateNextId(): String = transaction {
        val schoolId = config.propertyOrNull("app.schoolCode")?.getString() ?: "MIT"
        val currentYear = LocalDateTime.now().year
        val yearSuffix = (currentYear % 100).toString().padStart(2, '0')
        val prefix = "$yearSuffix$schoolId"
        val remainingLength = STUDENT_IDENTIFIER_LENGTH - prefix.length

        // Use upsert to handle concurrent access better
        val currentSequence = SequenceTable.selectAll()
            .where { SequenceTable.prefix eq prefix }
            .singleOrNull()
            ?.get(SequenceTable.lastSequence) ?: 0

        val nextSequence = currentSequence + 1

        // Update or insert the sequence
        SequenceTable.upsert {
            it[SequenceTable.prefix] = prefix
            it[SequenceTable.lastSequence] = nextSequence
        }



        val sequenceString = nextSequence.toString().padStart(remainingLength, '0')
        "$prefix$sequenceString"
    }

}
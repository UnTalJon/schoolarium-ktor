package com.schoolarium.infrastructure.repositories

import com.schoolarium.domain.model.Student
import com.schoolarium.domain.repository.StudentRepository
import com.schoolarium.infrastructure.database.tables.StudentTable
import com.schoolarium.routing.request.StudentRequest
import com.schoolarium.util.dbQuery
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.util.*

class StudentRepositoryImp : StudentRepository {
    override suspend fun findAll(): List<Student> = dbQuery {
        StudentTable.selectAll().map { row ->
            Student(
                id = row[StudentTable.id].value,
                identifier = row[StudentTable.identifier],
                name = row[StudentTable.name],
                firstSurname = row[StudentTable.firstSurname],
                secondSurname = row[StudentTable.secondSurname]
            )
        }
    }

    override suspend fun findById(id: UUID): Student? = dbQuery {
        StudentTable.selectAll()
            .where { StudentTable.id eq id }
            .map { row ->
                Student(
                    id = row[StudentTable.id].value,
                    identifier = row[StudentTable.identifier],
                    name = row[StudentTable.name],
                    firstSurname = row[StudentTable.firstSurname],
                    secondSurname = row[StudentTable.secondSurname]
                )
            }
            .singleOrNull()
    }

    override suspend fun findByIdentifier(identifier: String): Student? = dbQuery {
        StudentTable.selectAll()
            .where { StudentTable.identifier eq identifier }
            .map { row ->
                Student(
                    id = row[StudentTable.id].value,
                    identifier = row[StudentTable.identifier],
                    name = row[StudentTable.name],
                    firstSurname = row[StudentTable.firstSurname],
                    secondSurname = row[StudentTable.secondSurname]
                )
            }
            .singleOrNull()
    }

    override suspend fun save(student: StudentRequest): Student = dbQuery {
        try {
            val resultRow = StudentTable.insert {
                it[identifier] = student.identifier
                it[name] = student.name
                it[firstSurname] = student.firstSurname
                it[secondSurname] = student.secondSurname
            }

            Student(
                id = resultRow[StudentTable.id].value, // .value extrae el UUID del EntityID
                identifier = resultRow[StudentTable.identifier],
                name = resultRow[StudentTable.name],
                firstSurname = resultRow[StudentTable.firstSurname],
                secondSurname = resultRow[StudentTable.secondSurname]
            )
        } catch (e: Exception) {
            throw IllegalStateException("Failed to insert student ${student.name} due to error: ${e.message}")
        }
    }

    override suspend fun update(student: Student): Student? = dbQuery {
        val updatedRows = StudentTable.update({ StudentTable.id eq student.id }) {
            it[identifier] = student.identifier
            it[name] = student.name
            it[firstSurname] = student.firstSurname
            it[secondSurname] = student.secondSurname
        }
        if (updatedRows > 0) student else null
    }

    override suspend fun deleteById(id: UUID): Boolean = dbQuery {
        StudentTable.deleteWhere { StudentTable.id eq id } > 0
    }
}
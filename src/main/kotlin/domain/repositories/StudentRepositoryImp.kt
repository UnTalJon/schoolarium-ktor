package com.schoolarium.domain.repositories

import com.schoolarium.data.models.Student
import com.schoolarium.data.repositories.StudentRepository
import com.schoolarium.domain.IdGenerator
import com.schoolarium.routing.request.CreateStudentRequest
import com.schoolarium.routing.request.UpdateStudentRequest
import org.jetbrains.exposed.sql.transactions.transaction

class StudentRepositoryImp(
    private val generator: IdGenerator
) : StudentRepository {
    override suspend fun findAll(): List<Student> = transaction {
        Student.all()
            .sortedByDescending { it.id.value }
            .toList()

    }

    override suspend fun findById(id: String): Student? = transaction {
        Student.findById(id)
    }

    override suspend fun create(student: CreateStudentRequest): Student = transaction {
        val id = generator.generateNextId()

        Student.new(id) {
            name = student.name
            firstSurname = student.firstSurname
            secondSurname = student.secondSurname
            group = student.group
            grade = student.grade
            major = student.major
            profilePicturePath = student.profilePicturePath
        }
    }

    override suspend fun update(
        id: String,
        student: UpdateStudentRequest
    ): Student? = transaction {
        Student.findById(id)?.apply {
            student.name?.let { name = it }
            student.firstSurname?.let { firstSurname = it }
            student.secondSurname?.let { secondSurname = it }
            student.group?.let { group = it }
            student.grade?.let { grade = it }
            student.major?.let { major = it }
            student.profilePicturePath?.let { profilePicturePath = it }
        }
    }

    override suspend fun delete(id: String): Boolean = transaction {
        Student.findById(id)?.let {
            it.delete()
            true
        } ?: false
    }
}
package com.schoolarium.domain.services

import com.schoolarium.data.models.Student
import com.schoolarium.data.repositories.StudentRepository
import com.schoolarium.routing.request.StudentRequest
import java.util.UUID

class StudentService(
    private val studentRepository: StudentRepository
) {
    suspend fun findAll(): List<Student> = studentRepository.findAll()

    suspend fun findById(id: String): Student? = studentRepository.findById(UUID.fromString(id))

    suspend fun findByIdentifier(identifier: String): Student? = studentRepository.findByIdentifier(identifier)

    suspend fun save(student: StudentRequest): Student = studentRepository.save(student)

    suspend fun update(id: String, student: StudentRequest): Student? = studentRepository.update(UUID.fromString(id), student)

    suspend fun deleteById(id: UUID) = studentRepository.deleteById(id)
}
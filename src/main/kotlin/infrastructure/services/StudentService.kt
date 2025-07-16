package com.schoolarium.infrastructure.services

import com.schoolarium.domain.model.Student
import com.schoolarium.domain.repository.StudentRepository
import com.schoolarium.routing.request.StudentRequest
import java.util.UUID

class StudentService(
    private val studentRepository: StudentRepository
) {
    suspend fun findAll(): List<Student> = studentRepository.findAll()

    suspend fun findById(id: UUID): Student? = studentRepository.findById(id)

    suspend fun findByIdentifier(identifier: String): Student? = studentRepository.findByIdentifier(identifier)

    suspend fun save(student: StudentRequest): Student = studentRepository.save(student)

    suspend fun update(student: Student): Student? = studentRepository.update(student)

    suspend fun deleteById(id: UUID) = studentRepository.deleteById(id)
}
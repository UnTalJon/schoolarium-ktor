package com.schoolarium.data.repositories

import com.schoolarium.data.models.Student
import com.schoolarium.routing.request.StudentRequest
import java.util.UUID

interface StudentRepository {
    suspend fun findAll(): List<Student>
    suspend fun findById(id: UUID): Student?
    suspend fun findByIdentifier(identifier: String): Student?
    suspend fun save(student: StudentRequest): Student
    suspend fun update(id: UUID, student: StudentRequest): Student?
    suspend fun deleteById(id: UUID): Boolean
}
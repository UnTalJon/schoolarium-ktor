package com.schoolarium.data.repositories

import com.schoolarium.data.models.Student
import com.schoolarium.routing.request.CreateStudentRequest
import com.schoolarium.routing.request.UpdateStudentRequest

interface StudentRepository {
    suspend fun findAll(): List<Student>
    suspend fun findById(id: String): Student?
    suspend fun create(student: CreateStudentRequest): Student
    suspend fun update(id: String, student: UpdateStudentRequest): Student?
    suspend fun delete(id: String): Boolean
}
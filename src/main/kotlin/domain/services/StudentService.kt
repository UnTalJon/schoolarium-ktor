package com.schoolarium.domain.services

import com.schoolarium.aws.S3Service
import com.schoolarium.data.ServiceResult
import com.schoolarium.data.repositories.StudentRepository
import com.schoolarium.database.FIVE_MB_IMAGE_SIZE
import com.schoolarium.domain.mappers.toResponse
import com.schoolarium.routing.request.CreateStudentRequest
import com.schoolarium.routing.request.UpdateStudentRequest
import com.schoolarium.routing.response.StudentResponse
import com.schoolarium.util.isValidImageType
import io.ktor.http.content.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*

class StudentService(
    private val studentRepository: StudentRepository,
    private val s3Service: S3Service
) {

    suspend fun findAll(): Result<List<StudentResponse>> {
        return try {
            Result.success(
                studentRepository.findAll()
                    .map { it.toResponse() }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun findById(id: String): StudentResponse {
        return try {
            studentRepository.findById(id)?.toResponse()
                ?: throw Exception("Student not found")
        } catch (e: Exception) {
            throw Exception("Student not found, ${e.message}")
        }
    }

    suspend fun create(call: RoutingCall): StudentResponse {
        return try {
            val student = if (call.request.isMultipart()) {
                processMultiPartCreateStudentRequest(call)
            } else {
                call.receive<CreateStudentRequest>()
            }

            studentRepository.create(student).toResponse()
        } catch (e: Exception) {
            throw Exception("An error occurred: ${e.message}")
        }
    }

    suspend fun update(call: RoutingCall): StudentResponse {
        return try {
            val id = call.parameters["id"] ?: throw Exception("Id not found")

            val student = if (call.request.isMultipart()) {
                processMultiPartUpdateStudentRequest(call)
            } else {
                call.receive<UpdateStudentRequest>()
            }

            studentRepository.update(id, student)?.toResponse() ?: throw Exception("Student with id $id not updated")
        } catch (e: Exception) {
            throw Exception("An error ocurred: ${e.message}")
        }
    }

    suspend fun delete(id: String): ServiceResult<Boolean> {
        return try {
            ServiceResult.Success(studentRepository.delete(id))
        } catch (e: Exception) {
            ServiceResult.Error(e.message ?: "An error occurred")
        }
    }

    private suspend fun processMultiPartCreateStudentRequest(request: RoutingCall): CreateStudentRequest {
        val multipartData = request.receiveMultipart(formFieldLimit = FIVE_MB_IMAGE_SIZE) // 5MB Máximo

        // Variables para almacenar los datos
        val student = CreateStudentRequest("default", "default")

        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    // Manejar campos de texto
                    when (part.name) {
                        "name" -> student.name = part.value
                        "firstSurname" -> student.firstSurname = part.value
                        "secondSurname" -> student.secondSurname = part.value
                        "group" -> student.group = part.value
                        "grade" -> student.grade = part.value
                        "major" -> student.major = part.value
                    }
                }

                is PartData.FileItem -> {
                    val fileName = part.originalFileName
                    val contentType = part.contentType?.toString()
                    val fileExtension = part.contentType?.contentSubtype.toString()

                    if (fileName.isNullOrBlank())
                        throw Exception("Image didn't upload")
                    if (contentType == null || !contentType.isValidImageType())
                        throw Exception("Image type didn't valid")

                    val fileBytes = part.provider().toByteArray()

                    student.profilePicturePath = s3Service.uploadImage(fileBytes, contentType, fileExtension)
                }

                else -> {}
            }
            part.dispose()
        }

        return student
    }

    private suspend fun processMultiPartUpdateStudentRequest(request: RoutingCall): UpdateStudentRequest {
        val multipartData = request.receiveMultipart(formFieldLimit = FIVE_MB_IMAGE_SIZE) // 5MB Máximo

        // Variables para almacenar los datos
        val student = UpdateStudentRequest()

        multipartData.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    // Manejar campos de texto
                    when (part.name) {
                        "name" -> student.name = part.value
                        "firstSurname" -> student.firstSurname = part.value
                        "secondSurname" -> student.secondSurname = part.value
                        "group" -> student.group = part.value
                        "grade" -> student.grade = part.value
                        "major" -> student.major = part.value
                    }
                }

                is PartData.FileItem -> {
                    val fileName = part.originalFileName
                    val contentType = part.contentType?.toString()
                    val fileExtension = part.contentType?.contentSubtype.toString()

                    if (fileName.isNullOrBlank())
                        throw Exception("Image didn't upload")
                    if (contentType == null || !contentType.isValidImageType())
                        throw Exception("Image type didn't valid")

                    val fileBytes = part.provider().toByteArray()
                    student.profilePicturePath = s3Service.uploadImage(fileBytes, contentType, fileExtension)
                }

                else -> {}
            }
            part.dispose()
        }

        return student
    }
}
package com.schoolarium.aws

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.model.GetObjectRequest
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.smithy.kotlin.runtime.content.ByteStream
import aws.sdk.kotlin.services.s3.presigners.presignGetObject
import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import io.ktor.server.application.*
import java.util.*
import kotlin.time.Duration.Companion.hours

class S3Service(
    private val app: Application
) {
    private val config = app.environment.config
    private val s3Client: S3Client

    private val secretKeyId = config.propertyOrNull("aws.accessKeyId")?.getString()
        ?: throw Exception("Access Key ID is missing")
    private val secretKey = config.propertyOrNull("aws.secretAccessKey")?.getString()
        ?: throw Exception("Secret Key is missing")
    private val secretRegion = config.propertyOrNull("aws.region")?.getString()
        ?: throw Exception("Region is missing")
    private val secretBucket = config.propertyOrNull("aws.bucket")?.getString()
        ?: throw Exception("Bucket name is missing")

    init {
        s3Client = S3Client {
            region = secretRegion
            credentialsProvider = StaticCredentialsProvider {
                accessKeyId = secretKeyId
                secretAccessKey = secretKey
            }
        }
    }

    suspend fun uploadImage(file: ByteArray, fileContentType: String, fileExtension: String): String = withContext(Dispatchers.IO) {
        try {
            val imageKey = "images/${UUID.randomUUID()}.$fileExtension"

            val request = PutObjectRequest {
                bucket = secretBucket
                key = imageKey
                contentType = fileContentType
                body = ByteStream.fromBytes(file)
            }

            s3Client.putObject(request)
            imageKey
        } catch (e: Exception) {
            println("S3 Upload Error: ${e.message}")
            e.printStackTrace()
            throw Exception("Error uploading image: ${e.message}")
        }
    }

    suspend fun getObjectPresigned(objectKey: String): String = withContext(Dispatchers.IO) {
        try {
            println("Generating presigned URL for: $objectKey")
            println("Bucket: $secretBucket")

            val unsignedRequest = GetObjectRequest {
                bucket = secretBucket
                key = objectKey
            }

            val presignedRequest = s3Client.presignGetObject(unsignedRequest, 1.hours)
            val url = presignedRequest.url.toString()

            println("Generated URL: $url")
            url
        } catch (e: Exception) {
            println("S3 Presign Error: ${e.message}")
            e.printStackTrace()
            throw Exception("Error generating presigned URL: ${e.message}")
        }
    }

    // Función para verificar si un objeto existe
    suspend fun objectExists(objectKey: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val headRequest = aws.sdk.kotlin.services.s3.model.HeadObjectRequest {
                bucket = secretBucket
                key = objectKey
            }
            s3Client.headObject(headRequest)
            true
        } catch (e: Exception) {
            false
        }
    }

    // Función para cerrar el cliente cuando la aplicación termine
    fun close() {
        s3Client.close()
    }
}
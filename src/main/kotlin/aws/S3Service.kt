package com.schoolarium.aws

import io.ktor.server.application.Application
import kotlinx.serialization.Serializable
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.util.UUID

class S3Service(
    private val app: Application
) {
    private val config = app.environment.config
    private val s3Client: S3Client

    private val accessKey = config.propertyOrNull("aws.accessKeyId")?.getString()
        ?: throw Exception("Access Key ID is missing")
    private val secretKey = config.propertyOrNull("aws.secretAccessKey")?.getString()
        ?: throw Exception("Secret Key is missing")
    private val region = config.propertyOrNull("aws.region")?.getString()
        ?: throw Exception("Region is missing")
    private val bucket = config.propertyOrNull("aws.bucket")?.getString()
        ?: throw Exception("Bucket name Key is missing")

    init {
        val credentials = AwsBasicCredentials.create(accessKey, secretKey)

        s3Client = S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()
    }

    suspend fun uploadImage(fileBytes: ByteArray, contentType: String, fileExtension: String): String {
        val key = "images/${UUID.randomUUID()}.$fileExtension"

        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType(contentType)
            .build()

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileBytes))

        return "https://$bucket.s3.$region.amazonaws.com/$key"
    }
}

@Serializable
data class UploadResponse(
    val success: Boolean,
    val message: String,
    val url: String? = null
)

@Serializable
data class MultipleUploadResponse(
    val success: Boolean,
    val message: String,
    val uploaded_count: Int = 0,
    val urls: List<String> = emptyList()
)

@Serializable
data class UrlUploadRequest(
    val url: String
)
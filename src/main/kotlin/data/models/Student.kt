package com.schoolarium.data.models

import com.schoolarium.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Student(
    val id: @Serializable(with = UUIDSerializer::class) UUID,
    val identifier: String,
    val name: String,
    val firstSurname: String?,
    val secondSurname: String?,
) {
    fun fullname(): String = "$name $firstSurname $secondSurname"
}
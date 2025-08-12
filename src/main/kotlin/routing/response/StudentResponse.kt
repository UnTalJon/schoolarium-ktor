package com.schoolarium.routing.response

import kotlinx.serialization.Serializable

@Serializable
data class StudentResponse(
    val id: String,
    val name: String,
    val firstSurname: String,
    val secondSurname: String? = null,
    val group: String? = null,
    val grade: String? = null,
    val major: String? = null,
    val profilePicturePath: String? = null,
)
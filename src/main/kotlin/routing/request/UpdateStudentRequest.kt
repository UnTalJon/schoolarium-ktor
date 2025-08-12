package com.schoolarium.routing.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateStudentRequest(
    var name: String? = null,
    var firstSurname: String? = null,
    var secondSurname: String? = null,
    var group: String? = null,
    var grade: String? = null,
    var major: String? = null,
    var profilePicturePath: String? = null,
)
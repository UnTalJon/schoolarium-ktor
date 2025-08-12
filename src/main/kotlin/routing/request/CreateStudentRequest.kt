package com.schoolarium.routing.request

import kotlinx.serialization.Serializable


@Serializable
data class CreateStudentRequest(
    var name: String,
    var firstSurname: String,
    var secondSurname: String? = null,
    var group: String? = null,
    var grade: String? = null,
    var major: String? = null,
    var profilePicturePath: String? = null,
) {
    init {
        require(name.isNotBlank()) { "name is required" }
        require(firstSurname.isNotBlank()) { "first surname is required" }
    }
}

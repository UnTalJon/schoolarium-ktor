package com.schoolarium.routing.request

import kotlinx.serialization.Serializable

@Serializable
data class StudentRequest(
    val identifier: String,
    val name: String,
    val firstSurname: String? = null,
    val secondSurname: String? = null,
) {
    init {
        require(identifier.isNotBlank()) { "identifier is required" }
        require(name.isNotBlank()) { "name is required" }
    }
}

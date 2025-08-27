package com.schoolarium.domain.mappers

import com.schoolarium.data.models.Student
import com.schoolarium.routing.response.StudentResponse

fun Student.toResponse(presignedUrl: String? = null): StudentResponse = StudentResponse(
    id = this.id.value,
    name = this.name,
    firstSurname = this.firstSurname,
    secondSurname = this.secondSurname,
    group = this.group,
    grade = this.grade,
    major = this.major,
    profilePicturePath = presignedUrl ?: this.profilePicturePath,
)
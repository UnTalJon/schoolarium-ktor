package com.schoolarium.data.models

import com.schoolarium.database.tables.StudentTable
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.dao.Entity
import org.jetbrains.exposed.v1.dao.EntityClass


class Student(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, Student>(StudentTable)

    var name by StudentTable.name
    var firstSurname by StudentTable.firstSurname
    var secondSurname by StudentTable.secondSurname
    var group by StudentTable.group
    var grade by StudentTable.grade
    var major by StudentTable.major
    var profilePicturePath by StudentTable.profilePicturePath
}
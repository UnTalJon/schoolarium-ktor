package com.schoolarium.data.models

import com.schoolarium.database.tables.StudentTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID


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
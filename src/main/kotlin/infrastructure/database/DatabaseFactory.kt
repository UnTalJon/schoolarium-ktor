package com.schoolarium.infrastructure.database

import com.schoolarium.infrastructure.database.tables.StudentTable
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseFactory(private val app: Application) {
    val tables = arrayOf(StudentTable)

    fun connect() {
        val config = app.environment.config

        val url = config.property("database.url").getString()
        val driver = config.property("database.driver").getString()
        val username = config.property("database.username").getString()
        val password = config.property("database.password").getString()

        Database.Companion.connect(
            url = url,
            user = username,
            driver = driver,
            password = password,
        )

        transaction {
            SchemaUtils.create(*tables)
        }
    }
}
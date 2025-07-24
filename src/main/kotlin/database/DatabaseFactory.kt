package com.schoolarium.database

import com.schoolarium.database.tables.RecordTable
import com.schoolarium.database.tables.StudentTable
import io.ktor.server.application.Application
import io.ktor.server.application.port
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseFactory(private val app: Application) {
    val tables = arrayOf(StudentTable, RecordTable)

    fun connect() {
        val config = app.environment.config

        val type = config.property("database.type").getString()
        val driver = config.property("database.driver").getString()
        val host = config.property("database.host").getString()
        val port = config.property("database.port").getString()
        val name = config.property("database.name").getString()
        val username = config.property("database.username").getString()
        val password = config.property("database.password").getString()

        Database.Companion.connect(
            url = "jdbc:$type://$host:$port/$name",
            driver = driver,
            user = username,
            password = password,
        )

        transaction {
            SchemaUtils.create(*tables)
        }
    }
}
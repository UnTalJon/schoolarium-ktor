package com.schoolarium.database

import io.ktor.server.application.*
import org.koin.ktor.ext.get

fun Application.configureDatabases() {
    val database = DatabaseFactory(get())
    database.connect()
}

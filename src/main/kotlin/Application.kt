package com.schoolarium

import com.schoolarium.di.configureKoin
import com.schoolarium.database.configureDatabases
import com.schoolarium.domain.configureLogging
import com.schoolarium.routing.request.configureRequestValidation
import com.schoolarium.routing.configureRouting
import com.schoolarium.routing.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureLogging()
    configureKoin()
    configureSerialization()
    configureRequestValidation()
    configureDatabases()
    configureRouting()
}

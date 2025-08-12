package com.schoolarium.di.module

import com.schoolarium.database.DatabaseFactory
import io.ktor.server.application.Application
import org.koin.dsl.module

val databaseModule = module {
    single<DatabaseFactory> { DatabaseFactory(get<Application>()) }
}
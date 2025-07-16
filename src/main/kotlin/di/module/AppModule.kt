package com.schoolarium.di.module

import org.koin.dsl.module

val appModule = module {
    includes(databaseModule, repositoryModule, serviceModule)
}
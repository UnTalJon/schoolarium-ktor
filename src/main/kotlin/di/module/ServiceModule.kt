package com.schoolarium.di.module

import com.schoolarium.infrastructure.services.StudentService
import org.koin.dsl.module

val serviceModule = module {
    factory<StudentService> { StudentService(get()) }
}
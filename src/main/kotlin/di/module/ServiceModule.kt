package com.schoolarium.di.module

import com.schoolarium.domain.services.RecordService
import com.schoolarium.domain.services.StudentService
import org.koin.dsl.module

val serviceModule = module {
    factory<StudentService> { StudentService(get()) }
    factory<RecordService> { RecordService(get()) }
}
package com.schoolarium.di.module

import com.schoolarium.aws.S3Service
import com.schoolarium.domain.IdGenerator
import com.schoolarium.domain.services.RecordService
import com.schoolarium.domain.services.StudentService
import io.ktor.server.application.*
import org.koin.dsl.module

val serviceModule = module {
    single { IdGenerator(get<Application>()) }
    single { S3Service(get<Application>()) }
    factory<StudentService> { StudentService(get(), get()) }
    factory<RecordService> { RecordService(get()) }
}
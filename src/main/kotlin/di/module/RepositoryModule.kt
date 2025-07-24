package com.schoolarium.di.module

import com.schoolarium.data.repositories.RecordRepository
import com.schoolarium.data.repositories.StudentRepository
import com.schoolarium.domain.repositories.RecordRepositoryImp
import com.schoolarium.domain.repositories.StudentRepositoryImp
import org.koin.dsl.module

val repositoryModule = module {
    single<StudentRepository> { StudentRepositoryImp() }
    single<RecordRepository> { RecordRepositoryImp() }
}
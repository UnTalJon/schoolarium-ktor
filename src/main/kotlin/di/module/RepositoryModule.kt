package com.schoolarium.di.module

import com.schoolarium.domain.repository.StudentRepository
import com.schoolarium.infrastructure.repositories.StudentRepositoryImp
import org.koin.dsl.module

val repositoryModule = module {
    single<StudentRepository> { StudentRepositoryImp() }
}
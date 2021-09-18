package com.demo.takenote.di

import com.demo.takenote.data.repository.NoteRepository
import com.demo.takenote.data.repository.impl.NoteRepositoryImpl
import org.koin.dsl.module

/**
 *  Create by ThanhPQ
 */
val repositoryModule = module {
    single<NoteRepository> { NoteRepositoryImpl(get()) }
}
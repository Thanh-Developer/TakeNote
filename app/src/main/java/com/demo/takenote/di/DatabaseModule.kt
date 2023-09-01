package com.demo.takenote.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.demo.takenote.data.local.db.NoteDatabase
import org.koin.dsl.module

val dbModule = module {
    single { createDatabaseName() }
    single { createAppDatabase(get(), get()) }
    single { createMovieDao(get()) }
}

fun createDatabaseName() = "NoteDatabase"

fun createAppDatabase(dbName: String, context: Context) =
    databaseBuilder(context, NoteDatabase::class.java, NoteDatabase.DB_NAME).build()

fun createMovieDao(noteDatabase: NoteDatabase) = noteDatabase.getNoteDao()
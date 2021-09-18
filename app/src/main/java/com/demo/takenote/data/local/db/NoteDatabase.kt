package com.demo.takenote.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 *  Create by ThanhPQ
 */

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

    companion object {
        const val DB_NAME = "note_database.db"
    }
}
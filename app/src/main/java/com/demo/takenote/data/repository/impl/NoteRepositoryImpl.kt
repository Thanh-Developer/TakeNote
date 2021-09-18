package com.demo.takenote.data.repository.impl

import com.demo.takenote.data.local.db.Note
import com.demo.takenote.data.local.db.NoteDao
import com.demo.takenote.data.repository.NoteRepository

/**
 *  Create by ThanhPQ
 */
class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {

    override fun getAllNotes() = noteDao.getAllNotes()

    override suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    override suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    override suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    override suspend fun deleteNoteById(id: Int) = noteDao.deleteNoteById(id)

    override suspend fun clearNote() = noteDao.clearNote()
}
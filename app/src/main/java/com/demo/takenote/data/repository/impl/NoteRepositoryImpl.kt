package com.demo.takenote.data.repository.impl

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.demo.takenote.data.local.db.Note
import com.demo.takenote.data.local.db.NoteDao
import com.demo.takenote.data.repository.NoteRepository


/**
 *  Create by ThanhPQ
 */
class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {

    // Get data normal
    override fun getAllNotes() = noteDao.getAllNotes()

    // Get data by paging
    override fun getAllNotePaged(config: PagedList.Config): LiveData<PagedList<Note>> {
        val factory: DataSource.Factory<Int, Note> = noteDao.getAllNotePaged()
        return LivePagedListBuilder(factory, config).build()
    }

    override suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    override suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    override suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    override suspend fun deleteNoteById(id: Int) = noteDao.deleteNoteById(id)

    override suspend fun clearNote() = noteDao.clearNote()
}
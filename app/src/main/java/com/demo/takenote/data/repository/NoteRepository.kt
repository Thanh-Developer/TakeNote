package com.demo.takenote.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.demo.takenote.data.local.db.Note

/**
 *  Create by ThanhPQ
 */
interface NoteRepository {
    fun getAllNotes(): LiveData<List<Note>>
    fun getAllNotePaged(config: PagedList.Config): LiveData<PagedList<Note>>
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun deleteNoteById(id: Int)
    suspend fun clearNote()
}
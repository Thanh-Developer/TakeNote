package com.demo.takenote.ui.home

import androidx.lifecycle.ViewModel
import com.demo.takenote.data.local.db.Note
import com.demo.takenote.data.repository.NoteRepository

/**
 *  Create by ThanhPQ
 */
class HomeViewModel(private val repository: NoteRepository) : ViewModel() {

    suspend fun insertNote(note: Note) = repository.insertNote(note)

    suspend fun updateNote(note: Note) = repository.updateNote(note)

    suspend fun deleteNote(note: Note) = repository.deleteNote(note)

    suspend fun deleteNoteById(id: Int) = repository.deleteNoteById(id)

    suspend fun clearNote() = repository.clearNote()

    fun getAllNotes() = repository.getAllNotes()
}
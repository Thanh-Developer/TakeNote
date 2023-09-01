package com.demo.takenote.ui.home

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.demo.takenote.data.local.db.Note
import com.demo.takenote.data.repository.NoteRepository
import com.demo.takenote.utils.Constants


/**
 *  Create by ThanhPQ
 */
class HomeViewModel(private val repository: NoteRepository) : ViewModel() {

    suspend fun deleteNote(note: Note) = repository.deleteNote(note)

    suspend fun deleteNoteById(id: Int) = repository.deleteNoteById(id)

    suspend fun clearNote() = repository.clearNote()

    fun getAllNotes() = repository.getAllNotes()

    fun getAllNotePaged() = repository.getAllNotePaged(config)

    private val config = PagedList.Config.Builder()
        // Number of items loaded for a page in one go from DataSource
        .setPageSize(Constants.PAGE_SIZE)
        // The number of items in the first load, if not set, it defaults = 3 * pageSize
        .setInitialLoadSizeHint(Constants.PAGE_INITIAL_LOAD_SIZE_HINT)
        // Determine the distance (number of items) from the loaded content to load the data, if not set, it defaults to pageSize.
        .setPrefetchDistance(Constants.PAGE_PREFETCH_DISTANCE)
        // PagedList will display null placeholders for items that have not been loaded content, by default it will be true.
        .setEnablePlaceholders(true)
        .build()

}
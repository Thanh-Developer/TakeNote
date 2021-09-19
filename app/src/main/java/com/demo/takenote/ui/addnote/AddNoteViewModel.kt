package com.demo.takenote.ui.addnote

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.takenote.data.local.db.Note
import com.demo.takenote.data.repository.NoteRepository
import com.demo.takenote.utils.Coroutines
import com.demo.takenote.utils.DateUtils.getCurrentDateTime
import com.demo.takenote.utils.DateUtils.getFormattedDateString

/**
 *  Create by ThanhPQ
 */
class AddNoteViewModel(private val repository: NoteRepository) : ViewModel() {

    private suspend fun insertNote(note: Note) = repository.insertNote(note)

    private suspend fun updateNote(note: Note) = repository.updateNote(note)

    private suspend fun deleteNoteById(id: Int) = repository.deleteNoteById(id)

    var note = MutableLiveData<Note>().apply { value = null }
    var title = MutableLiveData<String>().apply { value = "" }
    var des = MutableLiveData<String>().apply { value = "" }
    var date = MutableLiveData<String>().apply {
        value = getFormattedDateString(getCurrentDateTime)
    }
    var isUpdateSuccess = MutableLiveData<Boolean>()
    var isInsertSuccess = MutableLiveData<Boolean>()
    var isValidateSuccess = MutableLiveData<Boolean>()
    var isDeleteSuccess = MutableLiveData<Boolean>()

    fun onSaveNote() {
        val id = note.value?.id
        val titleNote = title.value.toString().trim()
        val desc = des.value.toString().trim()
        val date = getFormattedDateString(getCurrentDateTime)
        val priority = 1

        if (titleNote.isEmpty() || desc.isEmpty()) {
            isValidateSuccess.value = false
            return
        }

        val note = Note(id, titleNote, desc, priority, date)
        Coroutines.main {
            if (id != null) {
                //for update note
                updateNote(note).also {
                    isUpdateSuccess.value = true
                }
            } else {
                //for insert note
                insertNote(note).also {
                    isInsertSuccess.value = true
                }
            }
        }
    }

    fun onCloseOrDelete() {
        val id = note.value?.id
        Coroutines.main {
            if (id != null) {
                deleteNoteById(id).apply {
                    isDeleteSuccess.value = true
                }
            }
        }
        isDeleteSuccess.value = false
    }

}

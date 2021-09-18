package com.demo.takenote.ui.addnote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.takenote.R
import com.demo.takenote.data.local.db.Note
import com.demo.takenote.databinding.ActivityAddNoteBinding
import com.demo.takenote.ui.home.HomeActivity
import com.demo.takenote.utils.Coroutines
import com.demo.takenote.utils.UtilExtensions.myToast
import com.demo.takenote.utils.UtilExtensions.setTextEditable
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *  Create by ThanhPQ
 */
class AddNoteActivity : AppCompatActivity() {
    private val viewModel: AddNoteViewModel by viewModel()
    private lateinit var binding: ActivityAddNoteBinding

    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        note = intent.extras?.getParcelable(HomeActivity.NOTE_DATA)

        initToolbar()
        initView()
        initClick()
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initView() {
        binding.apply {
            priorityPicker.minValue = 1
            priorityPicker.maxValue = 10
        }

        if (note != null) {
            binding.apply {
                titleET.setTextEditable(note?.title ?: "")
                descriptionET.setTextEditable(note?.description ?: "")
                priorityPicker.value = note?.priority ?: 1
                saveButton.text = getString(R.string.update_note)
            }
        }
    }

    private fun initClick() {
        binding.saveButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val id = if (note != null) note?.id else null
        val title = binding.titleET.text.toString().trim()
        val desc = binding.descriptionET.text.toString().trim()
        val priority = binding.priorityPicker.value

        if (title.isEmpty() || desc.isEmpty()) {
            myToast(getString(R.string.form_empty))
            return
        }

        val note = Note(id = id, title = title, description = desc, priority = priority)
        Coroutines.main {
            if (id != null) { //for update note
                viewModel.updateNote(note).also {
                    myToast(getString(R.string.success_update))
                    finish()
                }
            } else { //for insert note
                viewModel.insertNote(note).also {
                    myToast(getString(R.string.success_save))
                    finish()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
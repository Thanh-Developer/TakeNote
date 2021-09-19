package com.demo.takenote.ui.addnote

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.demo.takenote.R
import com.demo.takenote.data.local.db.Note
import com.demo.takenote.databinding.ActivityAddNoteBinding
import com.demo.takenote.ui.home.HomeActivity
import com.demo.takenote.utils.Coroutines
import com.demo.takenote.utils.DateUtils.getCurrentDateTime
import com.demo.takenote.utils.DateUtils.getFormattedDateString
import com.demo.takenote.utils.UtilExtensions.hideKeyboard
import com.demo.takenote.utils.UtilExtensions.myToast
import com.demo.takenote.utils.UtilExtensions.setTextEditable
import org.koin.android.viewmodel.ext.android.viewModel

class AddNoteActivity : AppCompatActivity(), View.OnClickListener {
    private val viewModel: AddNoteViewModel by viewModel()
    private lateinit var binding: ActivityAddNoteBinding
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
    }

    private fun initView() {
        binding.apply {
            toolBar.apply {
                btnClose.setOnClickListener(this@AddNoteActivity)
                btnDone.setOnClickListener(this@AddNoteActivity)
            }
            txtTime.text = getFormattedDateString(getCurrentDateTime)
        }
    }

    private fun initData() {
        note = intent.extras?.getParcelable(HomeActivity.NOTE_DATA)
        if (note != null) {
            binding.apply {
                edtTitle.setTextEditable(note?.title ?: "")
                edtDesc.setTextEditable(note?.description ?: "")
                txtTime.text = note?.date ?: ""

                toolBar.apply {
                    title.text = getString(R.string.update_note)
                    btnClose.setImageResource(R.drawable.ic_delete)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnClose -> handleClose()
            R.id.btnDone -> handleDone()
        }
    }

    private fun handleDone() {
        hideKeyboard(this)

        val id = if (note != null) note?.id else null
        val title = binding.edtTitle.text.toString().trim()
        val desc = binding.edtDesc.text.toString().trim()
        val date = getFormattedDateString(getCurrentDateTime)
        val priority = 1

        if (title.isEmpty() || desc.isEmpty()) {
            myToast(getString(R.string.form_empty))
            return
        }

        val note =
            Note(id = id, title = title, description = desc, priority = priority, date = date)
        Coroutines.main {
            if (id != null) {
                //for update note
                viewModel.updateNote(note).also {
                    myToast(getString(R.string.success_update))
                }
            } else {
                //for insert note
                viewModel.insertNote(note).also {
                    myToast(getString(R.string.success_save))
                }
            }
        }

        finish()
        overridePendingTransition(R.anim.stay, R.anim.slide_down)
    }

    private fun handleClose() {
        hideKeyboard(this)
        val id = if (note != null) note?.id else null
        Coroutines.main {
            if (id != null) {
                viewModel.deleteNoteById(id).apply {
                    myToast(getString(R.string.success_delete))
                }
            }
        }
        finish()
        overridePendingTransition(R.anim.stay, R.anim.slide_down)
    }

}

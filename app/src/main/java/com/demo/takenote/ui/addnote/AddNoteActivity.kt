package com.demo.takenote.ui.addnote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.takenote.R
import com.demo.takenote.data.local.db.Note
import com.demo.takenote.databinding.ActivityAddNoteBinding
import com.demo.takenote.ui.home.HomeActivity
import com.demo.takenote.utils.UtilExtensions.hideKeyboard
import com.demo.takenote.utils.UtilExtensions.myToast
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *  Create by ThanhPQ
 */
class AddNoteActivity : AppCompatActivity() {
    private val viewModel: AddNoteViewModel by viewModel()
    private lateinit var binding: ActivityAddNoteBinding
    private var noteData: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
        observeField()
    }

    private fun observeField() {
        with(viewModel) {
            isUpdateSuccess.observe(this@AddNoteActivity, {
                if (it) {
                    hideKeyboard(this@AddNoteActivity)
                    myToast(getString(R.string.success_update))
                    finish()
                    overridePendingTransition(R.anim.stay, R.anim.slide_down)
                }
            })

            isInsertSuccess.observe(this@AddNoteActivity, {
                if (it) {
                    hideKeyboard(this@AddNoteActivity)
                    myToast(getString(R.string.success_save))
                    finish()
                    overridePendingTransition(R.anim.stay, R.anim.slide_down)
                }
            })

            isValidateSuccess.observe(this@AddNoteActivity, {
                if (!it) {
                    myToast(getString(R.string.form_empty))
                }
            })

            isDeleteSuccess.observe(this@AddNoteActivity, {
                hideKeyboard(this@AddNoteActivity)
                if (it) {
                    myToast(getString(R.string.success_delete))
                }
                finish()
                overridePendingTransition(R.anim.stay, R.anim.slide_down)
            })
        }
    }

    private fun initView() {
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            viewModel = this@AddNoteActivity.viewModel
            lifecycleOwner = this@AddNoteActivity
            executePendingBindings()
        }
    }

    private fun initData() {
        noteData = intent.extras?.getParcelable(HomeActivity.NOTE_DATA)

        if (noteData != null) {
            viewModel.apply {
                note.value = noteData
                title.value = noteData?.title ?: ""
                des.value = noteData?.description ?: ""
                date.value = noteData?.date ?: ""
            }

            binding.toolBar.apply {
                title.text = getString(R.string.update_note)
                btnClose.setImageResource(R.drawable.ic_delete)
            }
        }
    }

}

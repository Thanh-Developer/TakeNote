package com.demo.takenote.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.takenote.R
import com.demo.takenote.databinding.ActivityMainBinding
import com.demo.takenote.ui.addnote.AddNoteActivity
import com.demo.takenote.utils.Coroutines
import com.demo.takenote.utils.UtilExtensions.myToast
import com.demo.takenote.utils.UtilExtensions.openActivity
import org.koin.android.viewmodel.ext.android.viewModel

/**
 *  Create by ThanhPQ
 */
class HomeActivity : AppCompatActivity() {
    companion object {
        const val NOTE_DATA = "NOTE_DATA"
    }

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //@todo bad practice because boilerplate code, but we'll be change this later using DI.

        homeAdapter = HomeAdapter {
            openActivity(AddNoteActivity::class.java) {
                putParcelable(NOTE_DATA, it)
            }
        }

        initView()
        observeNotes()
    }

    private fun initView() {
        binding.addNoteFAB.setOnClickListener {
            openActivity(AddNoteActivity::class.java)
        }

        binding.notesRV.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = homeAdapter
        }

        ItemTouchHelper(itemTouchHelperCallback()).attachToRecyclerView(binding.notesRV)
    }

    private fun observeNotes() {
        Coroutines.main {
            viewModel.getAllNotes().observe(this@HomeActivity, {
                homeAdapter.submitList(it)
            })
        }
    }

    private fun itemTouchHelperCallback(): ItemTouchHelper.SimpleCallback {
        return object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Coroutines.main {
                    val note = homeAdapter.getNoteAt(viewHolder.adapterPosition)
                    viewModel.deleteNote(note).also {
                        myToast(getString(R.string.success_delete))
                    }
                }
            }
        }
    }

    private fun clearNote() {
        val dialog = AlertDialog.Builder(this, R.style.ThemeOverlay_AppCompat_Dialog)
        dialog.setTitle(getString(R.string.clear_note))
            .setMessage(getString(R.string.sure_clear_note))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                Coroutines.main {
                    viewModel.clearNote().also {
                        myToast(getString(R.string.success_clear))
                    }
                }
            }.setNegativeButton(android.R.string.cancel, null).create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.clearNoteItem -> clearNote()
        }
        return super.onOptionsItemSelected(item)
    }
}
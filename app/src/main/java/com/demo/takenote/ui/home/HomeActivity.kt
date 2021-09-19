package com.demo.takenote.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeField()
    }

    private fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeAdapter = HomeAdapter {
            openActivity(AddNoteActivity::class.java) {
                putParcelable(NOTE_DATA, it)
            }
        }

        binding.fabAddNote.setOnClickListener {
            openActivity(AddNoteActivity::class.java)
        }

        binding.notesRV.apply {
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = homeAdapter
        }

        ItemTouchHelper(itemTouchHelperCallback()).attachToRecyclerView(binding.notesRV)
    }

    private fun observeField() {
        Coroutines.main {
            viewModel.getAllNotes().observe(this@HomeActivity, { listNote ->
                if (listNote.isNullOrEmpty()) {
                    binding.notesRV.visibility = View.GONE
                } else {
                    binding.notesRV.visibility = View.VISIBLE
                    homeAdapter.submitList(listNote)
                }
            })
        }
    }

    private fun itemTouchHelperCallback(): ItemTouchHelper.SimpleCallback {
        return object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                homeAdapter.onItemMove(
                    viewHolder.adapterPosition,
                    target.adapterPosition
                )
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

    companion object {
        const val NOTE_DATA = "NOTE_DATA"
    }
}

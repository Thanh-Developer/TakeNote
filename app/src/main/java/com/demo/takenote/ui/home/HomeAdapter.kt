package com.demo.takenote.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.takenote.data.local.db.Note
import com.demo.takenote.databinding.ItemNoteBinding
import java.util.*

/**
 *  Create by ThanhPQ
 */
class HomeAdapter(private val listener: (Note) -> Unit) :
    PagedListAdapter<Note, HomeAdapter.NoteViewHolder>(DiffUtilNote()) {
    lateinit var notes: List<Note>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bindItem(item, listener)
        }
    }

//    override fun submitList(list: List<Note>?) {
//        if (list != null) {
//            notes = list
//        }
//        super.submitList(list)
//    }

    fun getNoteAt(position: Int): Note {
        return getItem(position)!!
    }

    // Handle animation when delete note
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(notes, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(notes, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(note: Note, listener: (Note) -> Unit) {
            binding.apply {
                itemTitle.text = note.title
                itemDesc.text = note.description
                root.setOnClickListener {
                    listener(note)
                }
            }
        }
    }

    private class DiffUtilNote : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return newItem == oldItem
        }
    }
}
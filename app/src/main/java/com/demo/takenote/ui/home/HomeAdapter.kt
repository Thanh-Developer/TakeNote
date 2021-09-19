package com.demo.takenote.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.takenote.data.local.db.Note
import com.demo.takenote.databinding.ItemNoteBinding

/**
 *  Create by ThanhPQ
 */
class HomeAdapter(private val listener: (Note) -> Unit) :
    ListAdapter<Note, HomeAdapter.NoteViewHolder>(DiffUtilNote()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindItem(item, listener)
    }

    fun getNoteAt(position: Int): Note {
        return getItem(position)
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
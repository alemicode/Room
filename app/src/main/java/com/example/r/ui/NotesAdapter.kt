package com.example.r.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.r.R
import com.example.r.db.Note
import kotlinx.android.synthetic.main.note_layout.view.*

class NotesAdapter(val list: List<Note>) : RecyclerView.Adapter<NotesAdapter.NoteViewHoder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHoder {

        return NoteViewHoder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: NoteViewHoder, position: Int) {

        holder.view.tv_note.text = list[position].note
        holder.view.tv_title.text = list[position].title
    }

    class NoteViewHoder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}



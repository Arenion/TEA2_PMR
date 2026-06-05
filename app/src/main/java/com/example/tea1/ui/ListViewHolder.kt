package com.example.tea1.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tea1.R
import com.example.tea1.data.TodoList

class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView = itemView.findViewById<TextView>(R.id.list_title)

    fun bind(todoList: TodoList) {
        titleTextView.text = todoList.name
    }
}
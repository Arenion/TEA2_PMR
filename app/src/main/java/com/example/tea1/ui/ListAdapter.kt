package com.example.tea1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.tea1.R
import com.example.tea1.data.TodoList


class ListAdapter(
    private val onItemClick: (TodoList, Int) -> Unit,
    private val onDeleteClick: (TodoList, Int) -> Unit,
    private val onTextChanged: (String, Int) -> Unit
) : BaseAdapter<TodoList, ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val resourceLayoutId = R.layout.list_card
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resourceLayoutId,parent,false)
        return ListViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val todoList = dataSource[position]
        holder.bind(todoList)

        holder.titleEditText.setOnClickListener {
            onItemClick(todoList, position)
        }

        holder.titleEditText.doOnTextChanged { text, start, before, count ->
            onTextChanged(text.toString(), position)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(todoList, position)
        }
    }
}
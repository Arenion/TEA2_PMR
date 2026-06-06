package com.example.tea1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.example.tea1.R
import com.example.tea1.data.TodoItem


class TodoAdapter(
    private val onCheckboxClick: (Boolean, Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit,
    private val onTextChanged: (String, Int) -> Unit
) : BaseAdapter<TodoItem, TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val resourceLayoutId = R.layout.todo_card
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resourceLayoutId,parent,false)
        return TodoViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoItem = dataSource[position]
        holder.bind(todoItem)

        holder.titleEditText.doOnTextChanged { text, start, before, count ->
            onTextChanged(text.toString(), position)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(position)
        }

        holder.checkBox.setOnCheckedChangeListener { button, bool ->
            onCheckboxClick(bool, position)
        }
    }
}
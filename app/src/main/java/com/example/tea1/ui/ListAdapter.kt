package com.example.tea1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tea1.R
import com.example.tea1.data.TodoItem
import com.example.tea1.data.TodoList


class ListAdapter(
    private val onItemClick: (TodoList) -> Unit,
    private val onDeleteClick: (TodoList, Int) -> Unit
) : RecyclerView.Adapter<ListViewHolder>() {
    private var dataSource  = mutableListOf<TodoList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val resourceLayoutId = R.layout.list_card
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resourceLayoutId,parent,false)
        return ListViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val todoList = dataSource[position]
        holder.bind(todoList = todoList)

        holder.titleTextView.setOnClickListener {
            onItemClick(todoList)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(todoList, position)
        }
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    fun show(todoLists: List<TodoList>) {
        dataSource = todoLists.toMutableList()
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        dataSource.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(item: TodoList) {
        dataSource.add(0, item)
        notifyItemInserted(0)
    }
}
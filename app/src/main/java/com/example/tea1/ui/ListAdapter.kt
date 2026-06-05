package com.example.tea1.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tea1.R
import com.example.tea1.data.TodoList


class ListAdapter() : RecyclerView.Adapter<ListViewHolder>() {
    private var dataSource  = mutableListOf<TodoList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        Log.d("PostAdapter", "onCreateViewHolder")
        val resourceLayoutId = R.layout.list_card
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(resourceLayoutId,parent,false)
        return ListViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        Log.d("PostAdapter", "onBindViewHolder position:$position $holder")
        val todoList = dataSource[position]
        holder.bind(todoList = todoList)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

    fun show(todoLists: List<TodoList>) {
        dataSource = todoLists.toMutableList()
        notifyDataSetChanged()
    }
}
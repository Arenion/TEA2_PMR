package com.example.tea1.ui

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tea1.R
import com.example.tea1.data.Item
import com.example.tea1.data.TodoList

abstract class BaseViewHolder<T : Item>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleTextView: TextView = itemView.findViewById<TextView>(R.id.list_title)
    val deleteButton: ImageButton = itemView.findViewById(R.id.btn_delete)

    fun bind(item: T) {
        titleTextView.text = item.name
    }
}
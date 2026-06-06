package com.example.tea1.ui

import android.view.View
import android.widget.CheckBox
import com.example.tea1.R
import com.example.tea1.data.TodoItem

class TodoViewHolder(itemView: View) : BaseViewHolder<TodoItem>(itemView) {
    val checkBox: CheckBox = itemView.findViewById(R.id.item_checkbox)

    override fun bind(item: TodoItem) {
        super.bind(item)
        checkBox.isChecked = item.state
    }
}
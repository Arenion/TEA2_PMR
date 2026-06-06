package com.example.tea1.ui

import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.tea1.R
import com.example.tea1.data.Item

abstract class BaseViewHolder<T : Item>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleEditText: EditText = itemView.findViewById<EditText>(R.id.list_title)
    val deleteButton: ImageButton = itemView.findViewById(R.id.btn_delete)

    open fun bind(item: T) {
        titleEditText.setText(item.name)
    }
}
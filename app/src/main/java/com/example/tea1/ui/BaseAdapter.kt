package com.example.tea1.ui

import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder>() : RecyclerView.Adapter<VH>() {
    protected var dataSource  = mutableListOf<T>()

    override fun getItemCount(): Int = dataSource.size

    fun show(newItems: List<T>) {
        dataSource = newItems.toMutableList()
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        dataSource.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(item: T, index: Int=0) {
        dataSource.add(index, item)
        notifyItemInserted(0)
    }
}
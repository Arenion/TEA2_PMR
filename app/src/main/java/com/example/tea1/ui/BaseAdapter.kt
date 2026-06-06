package com.example.tea1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tea1.R


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

    fun changeItem(item: T, index: Int=0) {
        dataSource[index] = item
        notifyItemChanged(index)
    }
}
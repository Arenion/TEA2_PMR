package com.example.tea1.ui

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.tea1.data.TodoList

class ListViewHolder(itemView: View) : BaseViewHolder<TodoList>(itemView) {
    override fun bind(item: TodoList) {
        super.bind(item)

        // Activate editing capabilities ONLY on Long Press
        titleEditText.setOnLongClickListener { view ->
            val editText = view as EditText

            // Unlock focus attributes programmatically
            editText.isFocusable = true
            editText.isFocusableInTouchMode = true

            // Force the typing cursor inside the box
            editText.requestFocus()
            editText.setSelection(editText.text.length) // Place cursor at the end of the text

            // Force the keyboard to slide open
            val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)

            true // Return true to signal the long press event was consumed
        }

        // Re-lock the field when the user finishes typing ("Done" key)
        titleEditText.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Re-lock the focus parameters
                titleEditText.isFocusable = false
                titleEditText.isFocusableInTouchMode = false
                titleEditText.clearFocus()

                // Hide keyboard
                val imm = textView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(textView.windowToken, 0)
                true
            } else {
                false
            }
        }
    }
}
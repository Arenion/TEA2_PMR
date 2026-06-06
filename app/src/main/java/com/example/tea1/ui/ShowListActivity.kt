package com.example.tea1.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tea1.R
import com.example.tea1.data.DataProvider.getUserProfile
import com.example.tea1.data.DataProvider.saveUserProfile
import com.example.tea1.data.TodoItem
import com.example.tea1.data.UserProfile

class ShowListActivity : BaseActivity(R.layout.activity_show_list, "Show list activity") {
    private lateinit var user : String
    private lateinit var userProfile : UserProfile
    private var listPosition : Int = -1
    private lateinit var newTodoInput: EditText
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getStringExtra("USER_PSEUDO") ?: ""
        listPosition = intent.getIntExtra("LIST_POS", -1)
        userProfile = getUserProfile(this, user)

        val list = findViewById<RecyclerView>(R.id.list_item)
        adapter = TodoAdapter(
            onCheckboxClick = { state, position ->
                userProfile.lists[listPosition].tasks[position].state = state
            },
            onDeleteClick = { position ->
                // Handle delete tap
                AlertDialog.Builder(this)
                    .setTitle("Confirmation de suppression")
                    .setMessage("Êtes vous sûr de vouloir supprimer cette tâche ?")
                    .setPositiveButton("Supprimer la tâche") { _, _ ->
                        userProfile.lists[listPosition].tasks.removeAt(position)
                        adapter.removeItem(position)
                    }
                    .setNegativeButton("Annuler", null)
                    .show()
            },
            onTextChanged = { newText, position ->
                userProfile.lists[listPosition].tasks[position].name = newText
            }
        )

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)

        adapter.show(userProfile.lists[listPosition].tasks)

        newTodoInput = findViewById(R.id.edit_List2)

        val okList = findViewById<Button>(R.id.OK_list2)
        okList.setOnClickListener {
            val todoName = newTodoInput.text.toString()
            newTodoInput.text.clear()
            val newTodo = TodoItem(todoName)
            userProfile.lists[listPosition].tasks.add(0, newTodo)
            adapter.addItem(newTodo)
        }
    }

    override fun onPause() {
        saveUserProfile(this, userProfile)
        super.onPause()
    }
}
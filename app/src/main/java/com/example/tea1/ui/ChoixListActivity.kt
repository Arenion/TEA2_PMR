package com.example.tea1.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tea1.R
import com.example.tea1.data.DataProvider.getUserProfile
import com.example.tea1.data.DataProvider.saveUserProfile
import com.example.tea1.data.TodoItem
import com.example.tea1.data.TodoList
import com.example.tea1.data.UserTodos

class ChoixListActivity : BaseActivity(R.layout.activity_choix_list, "Choix list activity") {
    private lateinit var user : String
    private lateinit var userTodos : UserTodos
    private lateinit var newListInput: EditText
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getStringExtra("USER_PSEUDO") ?: ""

        val list = findViewById<RecyclerView>(R.id.list_item)
        adapter = ListAdapter(
            onItemClick = { clickedItem ->
                // Handle standard row tap (e.g., open the list details)
                Toast.makeText(this, "Opening: $clickedItem", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { itemToDelete, position ->
                // Handle delete tap
                AlertDialog.Builder(this)
                    .setTitle("Confirmation de suppression")
                    .setMessage("Êtes vous sûr de vouloir supprimer la liste ?")
                    .setPositiveButton("Supprimer la liste") { _, _ ->
                        userTodos.lists.removeAt(position)
                        saveUserProfile(this, userTodos)
                        adapter.removeItem(position)
                    }
                    .setNegativeButton("Annuler", null)
                    .show()
            }
        )

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)

        userTodos = getUserProfile(this, user)

        adapter.show(userTodos.lists)

        newListInput = findViewById(R.id.edit_List2)

        val okList = findViewById<Button>(R.id.OK_list2)
        okList.setOnClickListener {
            val listName = newListInput.text.toString()
            newListInput.text.clear()
            val newTodoList = TodoList(listName)
            userTodos.lists.add(0, newTodoList)
            saveUserProfile(this, userTodos)
            adapter.addItem(newTodoList)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
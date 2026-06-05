package com.example.tea1.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tea1.R
import com.example.tea1.data.DataProvider.getUserProfile
import com.example.tea1.data.DataProvider.saveUserProfile
import com.example.tea1.data.TodoList
import com.example.tea1.data.UserTodos

class ChoixListActivity : BaseActivity(R.layout.activity_choix_list, "Choix list activity") {
    private lateinit var user : String
    private lateinit var userTodos : UserTodos
    private lateinit var newListInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = intent.getStringExtra("USER_PSEUDO") ?: ""

        val list = findViewById<RecyclerView>(R.id.list_item)
        val adapter = ListAdapter()

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)

        userTodos = getUserProfile(this, user)

        adapter.show(userTodos.lists)

        newListInput = findViewById(R.id.edit_List2)

        val okList = findViewById<Button>(R.id.OK_list2)
        okList.setOnClickListener {
            val listName = newListInput.text.toString()
            userTodos.lists.add(TodoList(listName))
            saveUserProfile(this, userTodos)
            adapter.show(userTodos.lists)
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
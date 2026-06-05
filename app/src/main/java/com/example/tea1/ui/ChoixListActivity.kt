package com.example.tea1.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tea1.R
import com.example.tea1.data.DataProvider.getUserProfile
import com.example.tea1.data.DataProvider.saveUserProfile
import com.example.tea1.data.TodoList
import com.example.tea1.data.UserTodos

class ChoixListActivity : AppCompatActivity() {
    private lateinit var user : String
    private lateinit var userTodos : UserTodos
    private lateinit var newListInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_choix_list)

        user = intent.getStringExtra("USER_PSEUDO") ?: ""

        val toolbar = findViewById<Toolbar>(R.id.toolbarchoix)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Choix list activity"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val insetsType = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            val systemBars = insets.getInsets(insetsType)
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val list = findViewById<RecyclerView>(R.id.list_item)
        val adapter = ListAdapter()

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this)

        userTodos = getUserProfile(this, user)

        adapter.show(userTodos.lists)

        newListInput = findViewById(R.id.edit_List2)

        val ok_list = findViewById<Button>(R.id.OK_list2)
        ok_list.setOnClickListener {
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
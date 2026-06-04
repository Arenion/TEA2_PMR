package com.example.tea1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var pseudoInput: AutoCompleteTextView
    private lateinit var users: Set<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        sharedPref = this.getSharedPreferences("users_data", Context.MODE_PRIVATE) ?: return
        users = sharedPref.getStringSet("users", emptySet()) ?: emptySet()

        pseudoInput = findViewById(R.id.pseudoinput)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "main activity"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button_ok = findViewById<Button>(R.id.OK_main)
        button_ok.setOnClickListener {
            val pseudo = pseudoInput.text.toString()
            with(sharedPref.edit()) {
                putString("current_user", pseudo)
                if (!users.contains(pseudo))
                {
                    val newUsers = users.toMutableSet()
                    newUsers.add(pseudo)
                    putStringSet("users", newUsers)
                }
                apply()
            }
            val intent = android.content.Intent(this, ChoixListActivity::class.java)
            intent.putExtra("USER_PSEUDO", pseudo)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        val currentUser = sharedPref.getString("current_user", "") ?: ""
        pseudoInput.setText(currentUser, false)
        pseudoInput.setSelection(currentUser.length) // To move the cursor at the end of the text

        users = sharedPref.getStringSet("users", emptySet()) ?: emptySet()

        val usersArray = users.toTypedArray()

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            usersArray
        )
        pseudoInput.setAdapter(adapter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = android.content.Intent(this, Settings_Activity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

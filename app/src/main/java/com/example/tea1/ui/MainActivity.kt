package com.example.tea1.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.example.tea1.R

class MainActivity : BaseActivity(R.layout.activity_main, "main activity") {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var pseudoInput: AutoCompleteTextView
    private lateinit var users: Set<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = this.getSharedPreferences("users_data", MODE_PRIVATE) ?: return
        users = sharedPref.getStringSet("users", emptySet()) ?: emptySet()

        pseudoInput = findViewById(R.id.pseudoinput)

        val buttonOk = findViewById<Button>(R.id.OK_main)
        buttonOk.setOnClickListener {
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
            val intent = Intent(this, ChoixListActivity::class.java)
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
}

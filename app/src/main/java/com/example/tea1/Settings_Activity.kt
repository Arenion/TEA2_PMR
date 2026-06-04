package com.example.tea1

import android.os.Bundle
import android.preference.PreferenceActivity
import android.view.Display
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
class Settings_Activity : AppCompatActivity() {
    data class Listvalue(
        val pseudo: String,
        val valeur: kotlin.collections.List<String>,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<Toolbar>(R.id.toolbarsettings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Préférences"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button =findViewById<Button>(R.id.OK_list)
        button.setOnClickListener {
            var valeur = mutableListOf<String>()
            val SharedPref = getSharedPreferences("user_data",MODE_PRIVATE)
            val pseudo = SharedPref.getString("current_user","utlisateur non existant").toString()

            val newvaleur =Listvalue(pseudo,valeur)
            var gson = Gson()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }


}
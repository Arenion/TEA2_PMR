package com.example.tea1.ui

import android.os.Bundle
import com.example.tea1.R

class SettingsActivity : BaseActivity(R.layout.activity_settings, "Préférences") {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings_container, SettingsFragment())
                .commit()
        }
    }
}
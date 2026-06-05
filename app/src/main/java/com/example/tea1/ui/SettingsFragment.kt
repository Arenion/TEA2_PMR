package com.example.tea1.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.preference.ListPreference
import androidx.preference.MultiSelectListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.tea1.R
import com.example.tea1.data.DataProvider.deleteAllUserProfiles
import com.example.tea1.data.DataProvider.deleteUserProfiles

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.sharedPreferencesName = "users_data"
        setPreferencesFromResource(R.xml.settings_preferences, rootKey)

        setupUserOptions()
        setupWipeOption()
    }

    private fun setupUserOptions() {
        val sharedPref = requireContext().getSharedPreferences("users_data", Context.MODE_PRIVATE)
        val usersSet = sharedPref.getStringSet("users", emptySet()) ?: emptySet()
        val usersArray = usersSet.toTypedArray()

        // Setup "Change Current User"
        val currentUserPref = findPreference<ListPreference>("current_user")
        currentUserPref?.apply {
            entries = usersArray
            entryValues = usersArray
            summary = sharedPref.getString("current_user", "None selected") // Show the active user in the summary description

            setOnPreferenceChangeListener { _, newValue ->
                summary = newValue.toString()
                true // Allow system to automatically update "current_user" in SP
            }
        }

        // Setup "Remove Usernames"
        val deleteUsersPref = findPreference<MultiSelectListPreference>("delete_users")
        deleteUsersPref?.apply {
            entries = usersArray
            entryValues = usersArray
            values = emptySet() // Keep check-boxes unchecked initially

            setOnPreferenceChangeListener { _, newValue ->
                val selectedToIdDelete = newValue as Set<*>
                if (selectedToIdDelete.isNotEmpty()) {
                    val remainingUsers = usersSet.toMutableSet()
                    val toRemoveUsers = selectedToIdDelete.map { it.toString() }
                    remainingUsers.removeAll(toRemoveUsers.toSet())

                    sharedPref.edit().apply {
                        putStringSet("users", remainingUsers)
                        // If the currently logged-in user is deleted, clear current_user field
                        if (selectedToIdDelete.contains(sharedPref.getString("current_user", ""))) {
                            remove("current_user")
                        }
                        apply()
                    }

                    deleteUserProfiles(requireContext(), toRemoveUsers)

                    Toast.makeText(context, "Selected users removed", Toast.LENGTH_SHORT).show()

                    // Refresh choices dynamically on the screen
                    setupUserOptions()
                }
                false // False prevents the MultiSelectList from storing its own temporary check status
            }
        }
    }

    private fun setupWipeOption() {
        // 3. Setup "Wipe All Preferences"
        val wipePref = findPreference<Preference>("wipe_all")
        wipePref?.setOnPreferenceClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Confirm Reset")
                .setMessage("Are you sure you want to delete all user profiles and settings?")
                .setPositiveButton("Wipe everything") { _, _ ->
                    val sharedPref = requireContext().getSharedPreferences("users_data", Context.MODE_PRIVATE)
                    sharedPref.edit().clear().apply()
                    deleteAllUserProfiles(requireContext())
                    Toast.makeText(context, "All preferences deleted", Toast.LENGTH_SHORT).show()
                    setupUserOptions() // Refresh layouts to blank states
                }
                .setNegativeButton("Cancel", null)
                .show()
            true
        }
    }
}
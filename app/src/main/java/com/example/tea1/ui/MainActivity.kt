package com.example.tea1.ui

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.example.tea1.R
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.content.Context
import android.widget.EditText
import androidx.annotation.RequiresPermission
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
class MainActivity : BaseActivity(R.layout.activity_main, "main activity") {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var pseudoInput: AutoCompleteTextView
    private lateinit var users: Set<String>
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = this.getSharedPreferences("users_data", MODE_PRIVATE) ?: return
        users = sharedPref.getStringSet("users", emptySet()) ?: emptySet()
        val api_root =sharedPref.getString("def_URL","http://tomnab.fr/todo-api/")
        println(api_root)
        pseudoInput = findViewById(R.id.pseudoinput)
        val viewPassword = findViewById<EditText>(R.id.Password_API)
        val buttonOk = findViewById<Button>(R.id.OK_main)
        buttonOk.setOnClickListener {
            val pseudo = pseudoInput.text.toString()
            val api_path="authenticate?user=$pseudo&password=${viewPassword.text}"
            lifecycleScope.launch {
                val hash = GetRequest(api_root, "GET", api_path)
                with(sharedPref.edit()) {
                    putString("current_user", pseudo)
                    if (!users.contains(pseudo)) {
                        val newUsers = users.toMutableSet()
                        newUsers.add(pseudo)
                        putStringSet("users", newUsers)
                    }
                    apply()
                }
                val intent = Intent(this@MainActivity, ChoixListActivity::class.java)
                intent.putExtra("USER_PSEUDO", pseudo)
                //intent.putExtra("HASH",hash)
                startActivity(intent)
            }
        }

        buttonOk.isEnabled = false
        if (checkinternet(this)){
            buttonOk.isEnabled= true
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
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)

    private fun checkinternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    private suspend fun GetRequest(API_ROOT: String?, Method: String, PATH: String): String = withContext(Dispatchers.IO) {
        if (API_ROOT == null) return@withContext ""
        val fullUrl = if (API_ROOT.endsWith("/")) API_ROOT + PATH else "$API_ROOT/$PATH"
        val url = try {
            URL(fullUrl)
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext ""
        }

        val connection = url.openConnection() as HttpURLConnection
        try {
            connection.requestMethod = Method.uppercase()
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                println("GET Response Body: $response")
                response
            } else {
                println("GET request failed: $responseCode")
                ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        } finally {
            connection.disconnect()
        }
    }
}

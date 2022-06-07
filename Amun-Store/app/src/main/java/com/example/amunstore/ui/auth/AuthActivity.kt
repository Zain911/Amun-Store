package com.example.amunstore.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.amunstore.MainActivity
import com.example.amunstore.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefVal = getString(R.string.preference_loggin)
        val sp = getSharedPreferences(prefVal, MODE_PRIVATE)

        if (sp.getLong(prefVal, 0) != 0L) {
            val intent = Intent(application, MainActivity::class.java)
            startActivity(intent)
        }
        setContentView(R.layout.activity_auth)
    }
}
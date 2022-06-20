package com.example.amunstore.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.amunstore.MainActivity
import com.example.amunstore.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // hiding action bar
            this.supportActionBar?.hide()
        //if user is logged in go to MainActivity else let user sign in
        if (viewModel.isUserLoggedIn()) {
            val intent = Intent(application, MainActivity::class.java)
            startActivity(intent)
        }
        else{ setContentView(R.layout.activity_auth) }
    }
}
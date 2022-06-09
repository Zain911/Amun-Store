package com.example.amunstore.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.amunstore.MainActivity
import com.example.amunstore.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!viewModel.isUserLoggedIn()) {
            val intent = Intent(application, MainActivity::class.java)
            startActivity(intent)
        }
        else{ setContentView(R.layout.activity_auth) }
    }
}
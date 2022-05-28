package com.example.amunstore.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.amunstore.MainActivity
import com.example.amunstore.R
import com.example.amunstore.utils.SharedPreferenceUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(SharedPreferenceUtil.isLogged(context = applicationContext))
        {
            val intent = Intent(application, MainActivity::class.java)
            startActivity(intent)
        }

        setContentView(R.layout.activity_auth)
    }
}
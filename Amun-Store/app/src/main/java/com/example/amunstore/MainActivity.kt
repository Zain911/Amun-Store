package com.example.amunstore

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.amunstore.databinding.ActivityMainBinding
import com.example.amunstore.ui.details.ProductDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_categories, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        setFragment()
    }

    fun setFragment() {
        val productID  = 7782820643045
        // Create new fragment and transaction
        val newFragment: Fragment = ProductDetailsFragment(productID)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()

        // Replace whatever is in the fragment_container view with this fragment, and add the transaction to the back stack
        transaction.replace(R.id.nav_host_fragment_activity_main, newFragment)
        transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()
    }
}
package com.example.amunstore

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.amunstore.databinding.ActivityMainBinding
import com.example.amunstore.domain.util.InternetConnectivity
import com.example.amunstore.ui.main.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewmodel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var connectionLiveData: InternetConnectivity

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
        connectionLiveData = InternetConnectivity(applicationContext)
        connectionLiveData.observe(MainActivity@this) { isAvailable ->
            when (isAvailable) {
                true -> {
                    val id = navController.currentDestination!!.id
                    if (id == R.id.noConnectionFragment) {
                       /// Toast.makeText(this, getString(R.string.connected), Toast.LENGTH_SHORT).show()
                        navController.navigateUp()
                    }
                }

                false -> {
                    val id = navController.currentDestination!!.displayName
                    Log.d( "onCreate: ",id)
                    navController.navigate(R.id.searchFragment)
                }
            }
        }


        binding.searchIcon.setOnClickListener {
            navController.navigate(R.id.searchFragment)
        }

        binding.favourite.favouriteButton.setOnClickListener {
            navController.navigate(R.id.favouriteFragment)
        }
        binding.cartView.cartButton.setOnClickListener {
            navController.navigate(R.id.cartFragment)
        }

        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            // the IDs of fragments as defined in the `navigation_graph`
            if (nd.id == R.id.navigation_home || nd.id == R.id.navigation_categories
                || nd.id == R.id.navigation_notifications
            ) {
                navView.visibility = View.VISIBLE
                binding.toolbar.visibility = View.VISIBLE
            } else {
                navView.visibility = View.GONE
                binding.toolbar.visibility = View.GONE
            }
        }

        viewmodel.cartItemsCount.observe(this) {
            if (it != 0) {
                binding.cartView.cartItems.visibility = View.VISIBLE
                binding.cartView.cartItems.text = it.toString()
            } else {
                binding.cartView.cartItems.visibility = View.GONE
            }
        }

        viewmodel.favouriteItemsCount.observe(this) {
            if (it != 0) {
                binding.favourite.favouriteItems.visibility = View.VISIBLE
                binding.favourite.favouriteItems.text = it.toString()
            } else {
                binding.favourite.favouriteItems.visibility = View.GONE
            }
        }

        viewmodel.getCartItemsCount()
        viewmodel.getFavouriteItemsCount()


    }
}

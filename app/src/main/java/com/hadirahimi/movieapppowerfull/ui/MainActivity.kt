package com.hadirahimi.movieapppowerfull.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.hadirahimi.movieapppowerfull.R
import com.hadirahimi.movieapppowerfull.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    private lateinit var navController : NavController
    private lateinit var binding : ActivityMainBinding
    
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        viewBinding()
        setupNavController()
        setupBottomNavigation()
        bottomNavigationVisibleSettings()
    }
    
    private fun bottomNavigationVisibleSettings()
    {
        navController.addOnDestinationChangedListener { _ , destination , _ ->
            if (destination.id == R.id.splashFragment || destination.id == R.id.registerFragment || destination.id == R.id.detailFragment)
            {
                binding.bottomNav.visibility = View.GONE
            }else
            {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }
    
    private fun setupBottomNavigation()
    {
            binding.bottomNav.setupWithNavController(navController)
        
    }
    
    private fun setupNavController()
    {
        navController = findNavController(R.id.navHost)
        
    }
    
    private fun viewBinding()
    {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    
    override fun onNavigateUp() : Boolean
    {
        return navController.navigateUp() ||  super.onNavigateUp()
    }
}
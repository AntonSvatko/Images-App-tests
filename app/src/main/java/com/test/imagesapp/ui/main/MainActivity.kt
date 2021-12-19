package com.test.imagesapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.test.imagesapp.R
import com.test.imagesapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @VisibleForTesting
    lateinit var navController: NavController

    var requirePostpone = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = requestNavController()

        setupNavigationListener()
        setupUI()
    }

    private fun setupNavigationListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.mainTitle.text = destination.label
            if (destination.id == R.id.details) {
                requirePostpone = true
            }
        }
    }

    private fun setupUI() {
        NavigationUI.setupWithNavController(binding.mainBottomNavigation, navController)
    }

    private fun requestNavController(): NavController {
        return getNavHostFragment().navController
    }

    private fun getNavHostFragment(): NavHostFragment {
        return supportFragmentManager.findFragmentById(R.id.main_host) as NavHostFragment
    }


    fun hideUI() = with(binding) {
        mainBottomContainer.isGone = true
        mainToolbar.isGone = true
    }

    fun showUI() = with(binding) {
        mainBottomContainer.isGone = false
        mainToolbar.isGone = false
    }

}
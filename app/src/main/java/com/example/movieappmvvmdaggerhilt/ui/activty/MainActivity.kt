package com.example.movieappmvvmdaggerhilt.ui.activty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import com.example.movieappmvvmdaggerhilt.R
import com.example.movieappmvvmdaggerhilt.commons.getNavController
import com.example.movieappmvvmdaggerhilt.databinding.ActivityMainBinding
import com.example.movieappmvvmdaggerhilt.ui.view_models.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val vm: MainViewModel by viewModels()

    private lateinit var navController: NavController
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            navController = getNavController(binding.navHostFragmentContainer.id)
        }
    }

    override fun onBackPressed() {

    }
}
package com.example.athousandcourses.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.athousandcourses.R
import com.example.athousandcourses.databinding.ActivityMainBinding
import com.example.athousandcourses.presentation.customview.BottomView


class MainActivity : AppCompatActivity() {
    private  var binding: ActivityMainBinding? =null
    private lateinit var customView: BottomView
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val navController =   findNavController(R.id.nav_host_fragment_activity_main)
        customView = binding!!.navView

        customView.setButtonClickListener(object : BottomView.OnButtonClickListener {
            override fun onButton1Click() {

                navController.navigate(R.id.homeFragment,)
            }
            override fun onButton2Click() {

                navController.navigate(R.id.favoriteFragment)
            }
            override fun onButton3Click() {

                navController.navigate(R.id.accountFragment)
            }
        })

        // Обновляем выбранный пункт при смене фрагмента
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {

                    customView.setSelectedIndex(1)
                }
                R.id.favoriteFragment -> {

                    customView.setSelectedIndex(2)
                }
                R.id.accountFragment -> {

                    customView.setSelectedIndex(3)
                }
            }
        }
        sharedViewModel.showBottomViewLiveData.observe(this) { show ->
            showBottomView(show)
        }
    }


    fun showBottomView(it:Boolean){
        binding?.navView?.isVisible = it
    }
}
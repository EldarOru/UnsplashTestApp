package com.example.unsplashtestapp.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.unsplashtestapp.R
import com.example.unsplashtestapp.data.network.RetrofitClient
import com.example.unsplashtestapp.databinding.ActivityMainBinding
import com.example.unsplashtestapp.presentation.fragments.TopicFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnFragmentInteractionsListener {
    private lateinit var mainActivityBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        supportFragmentManager.beginTransaction()
            .replace(mainActivityBinding.mainContainer.id, TopicFragment())
            .commit()
    }

    override fun onAddBackStack(name: String, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(name)
            .replace(mainActivityBinding.mainContainer.id, fragment)
            .commit()
    }

    override fun onPopBackStack() {
        for(i in 0..supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStack()
        }
    }

}
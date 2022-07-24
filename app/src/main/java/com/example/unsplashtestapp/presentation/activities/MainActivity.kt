package com.example.unsplashtestapp.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.unsplashtestapp.R
import com.example.unsplashtestapp.data.network.RetrofitClient
import com.example.unsplashtestapp.databinding.ActivityMainBinding
import com.example.unsplashtestapp.presentation.fragments.TopicFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        supportFragmentManager.beginTransaction()
            .replace(mainActivityBinding.mainContainer.id, TopicFragment())
            .commit()

    }

    /*
    suspend fun getQuotes() {
        val retrofitClient = RetrofitClient()
        val response = try {
            retrofitClient.retrofitServices.getTopics()
        } catch (e: Exception) {
            Log.d("Eldar", e.toString())
            null
        }
        Log.d("Eldar", response?.body().toString())
    }

     */
}
package com.example.appquanlycongviec

import android.content.Context
import android.content.IntentFilter
import android.content.UriMatcher
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var navController: NavController
        lateinit var myViewModel: WorkViewModel
        lateinit var lifecycleOwner: LifecycleOwner
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.containerView) as NavHostFragment
        navController = navHostFragment.navController
        myViewModel = ViewModelProvider(this).get(WorkViewModel::class.java)
        lifecycleOwner = this
        var intent = getIntent()
        var isDone = intent.getIntExtra("isDone", 0)
        if (isDone == 1) {
            onBackPressed()
        }
    }


}
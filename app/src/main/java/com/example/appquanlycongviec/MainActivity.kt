package com.example.appquanlycongviec

import android.content.Context
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
        var myUricode = 1
        lateinit var context : Context
        val PROVIDER : String = "com.example.appquanlycongviec.WorkContentProvider"
        val TABLE : String = "tbl_work"
        lateinit var uriMatcher : UriMatcher
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.containerView) as NavHostFragment
        navController = navHostFragment.navController
        context = this
        myViewModel = ViewModelProvider(this).get(WorkViewModel::class.java)
        lifecycleOwner = this
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        uriMatcher.addURI(PROVIDER, TABLE, myUricode)
    }
}
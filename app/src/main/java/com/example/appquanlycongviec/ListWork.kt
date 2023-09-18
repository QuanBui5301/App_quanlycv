package com.example.appquanlycongviec

import android.content.ContentValues
import android.net.Uri
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appquanlycongviec.MainActivity.Companion.lifecycleOwner
import com.example.appquanlycongviec.MainActivity.Companion.myViewModel
import com.example.appquanlycongviec.MainActivity.Companion.navController
import com.example.appquanlycongviec.WorkAdapter.Companion.WorkList
import com.example.appquanlycongviec.databinding.FragmentListWorkBinding


class ListWork : Fragment() {
    lateinit var binding: FragmentListWorkBinding
    lateinit var workAdapter: WorkAdapter
    private lateinit var workContentProvider: WorkContentProvider
    val uri : Uri = Uri.parse("content://com.example.appquanlycongviec.WorkContentProvider/tbl_work")
    companion object {
        lateinit var sqLiteHelper: SQLiteHelper
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListWorkBinding.inflate(layoutInflater)
        val view = binding.root
        sqLiteHelper = SQLiteHelper(view.context)
        WorkList = mutableListOf()
        workContentProvider = WorkContentProvider()
        myViewModel.currentWork.observe(lifecycleOwner, Observer {
            sqLiteHelper.insertWork(it)
        })
        WorkList = sqLiteHelper.getAllWork()
        workAdapter = WorkAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(view.context)
        binding.recyclerView.adapter = workAdapter
        binding.btnAdd.setOnClickListener() {
            navController.navigate(R.id.addWork)
        }
        return view
    }

}
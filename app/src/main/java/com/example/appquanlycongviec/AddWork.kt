package com.example.appquanlycongviec

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.appquanlycongviec.MainActivity.Companion.myViewModel
import com.example.appquanlycongviec.MainActivity.Companion.navController
import com.example.appquanlycongviec.WorkAdapter.Companion.WorkList
import com.example.appquanlycongviec.databinding.FragmentAddWorkBinding
import java.util.*


class AddWork : Fragment() {
    lateinit var binding: FragmentAddWorkBinding
    private var mSaveDay = 0
    private var mSaveMonth = 0
    private var mSaveYear = 0
    private lateinit var sqLiteHelper: SQLiteHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddWorkBinding.inflate(layoutInflater)
        val view = binding.root
        sqLiteHelper = SQLiteHelper(view.context)
        binding.timeEdit.setOnClickListener() {
            timePicker()
        }
        binding.btnSave.setOnClickListener() {
            if (binding.workEdit.text.toString() == "" || binding.timeEdit.text.toString() == "") {
                Toast.makeText(view.context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
            else {
                myViewModel.currentWork.value = Work(sqLiteHelper.getAllWork().size, binding.workEdit.text.toString(), binding.timeEdit.text.toString())
                navController.popBackStack()
            }
        }
        return view
    }

    private fun timePicker() {
        val timeCurent = Calendar.getInstance()
        var dayCurrent = timeCurent.get(Calendar.DAY_OF_MONTH)
        var monthCurrent = timeCurent.get(Calendar.MONTH)
        var yearCurrent = timeCurent.get(Calendar.YEAR)
        DatePickerDialog(binding.root.context, {_, year, month, day ->
            val pickedDateTime = Calendar.getInstance()
            pickedDateTime.set(year, month, day)
            mSaveYear = year
            mSaveMonth = month
            mSaveDay = day
            timeCurent.set(mSaveYear, mSaveMonth, mSaveDay)
            val time: Long = timeCurent.timeInMillis
            val isDay : Int =  boolToInt(mSaveDay<10)
            val isMonth : Int =  boolToInt(mSaveMonth<10)
            val isTime : String = "$isMonth$isDay"
            val timeHour : String = timeFormat(isTime)
            binding.timeEdit.setText(timeHour)
        }, yearCurrent, monthCurrent, dayCurrent).show()
    }
    private fun boolToInt(item : Boolean) : Int {
        return if (item) {
            1
        } else 0
    }
    fun timeFormat(isTime : String) : String {
        when (isTime) {
            "00" -> return "$mSaveDay/${mSaveMonth+1}/$mSaveYear"
            "10" -> return "$mSaveDay/0${mSaveMonth+1}/$mSaveYear"
            "01" -> return "0$mSaveDay/${mSaveMonth+1}/$mSaveYear"
            "11" -> return "0$mSaveDay/0${mSaveMonth+1}/$mSaveYear"
            else -> return "$mSaveDay/${mSaveMonth+1}/$mSaveYear"
        }
    }
}
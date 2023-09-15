package com.example.appquanlycongviec

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATEBASE_NAME, null, DATEBASE_VERSION){

    companion object {
        private const val DATEBASE_VERSION = 1
        private const val DATEBASE_NAME = "work.list"
        private const val TBL_FAVOURITE = "tbl_work"
        private const val ID = "id"
        private const val NAME = "name"
        private const val TIME = "time"
    }

    override fun onCreate(db: SQLiteDatabase?) {
            val createTbl = ("CREATE TABLE " + TBL_FAVOURITE + "(" + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT," + TIME + " TEXT" + ")")
        db?.execSQL(createTbl)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_FAVOURITE")
        onCreate(db)
    }
    fun insertWork(data : Work) : Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, data.id)
        contentValues.put(NAME, data.work)
        contentValues.put(TIME, data.time)
        val success = db.insert(TBL_FAVOURITE, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllWork() : ArrayList<Work> {
        val dataList : ArrayList<Work> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_FAVOURITE"
        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e : Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var name: String
        var time : String
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                time = cursor.getString(cursor.getColumnIndex("time"))
                val data = Work(id = id, work = name, time = time)
                dataList.add(data)
            } while (cursor.moveToNext())
        }
        return dataList
    }

    fun deleteWork(id:Int) : Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, id)
        val success = db.delete(TBL_FAVOURITE, "id=$id", null)
        db.close()
        return success
    }
}
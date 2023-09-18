package com.example.appquanlycongviec

import android.content.*
import android.database.Cursor
import android.net.Uri
import com.example.appquanlycongviec.ListWork.Companion.sqLiteHelper
import com.example.appquanlycongviec.MainActivity.Companion.PROVIDER
import com.example.appquanlycongviec.MainActivity.Companion.TABLE
import com.example.appquanlycongviec.MainActivity.Companion.myUricode
import com.example.appquanlycongviec.MainActivity.Companion.uriMatcher
import java.lang.IllegalArgumentException

class WorkContentProvider : ContentProvider() {
    override fun onCreate(): Boolean {
        return true
    }
    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        var cursor : Cursor? = null
        when (uriMatcher.match(p0)) {
            myUricode -> {
                cursor = sqLiteHelper.writableDatabase.query(TABLE, p1, p2, p3, null, null, p4)
                getContext()!!.getContentResolver().notifyChange(p0, null)
            }
            else -> {throw IllegalArgumentException("Unknown Uri: " + p0.toString())}
        }
        return cursor
    }

    override fun getType(p0: Uri): String? {
        when (uriMatcher.match(p0)) {
            myUricode -> {
                return PROVIDER + "/" + TABLE
            }
            else -> {throw IllegalArgumentException("Unknown Uri: " + p0.toString())}
        }
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        var rowId : Long = 0
        var uri : Uri? = null
        when (uriMatcher.match(p0)) {
            myUricode -> {
                rowId = sqLiteHelper.writableDatabase.insert(TABLE, null, p1)
                uri = ContentUris.withAppendedId(p0, rowId)
                MainActivity.context.contentResolver.notifyChange(uri, null)
                return uri
            }
            else -> {throw IllegalArgumentException("Unknown Uri: " + p0.toString())}
        }
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        var rowId : Int = 0
        when (uriMatcher.match(p0)) {
            myUricode -> {
                rowId = sqLiteHelper.writableDatabase.delete(TABLE, p1, p2)
                context!!.contentResolver.notifyChange(p0, null)
            }
            else -> {throw IllegalArgumentException("Unknown Uri: " + p0.toString())}
        }
        return rowId
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        var rowId : Int = 0
        when (uriMatcher.match(p0)) {
            myUricode -> {
                rowId = sqLiteHelper.writableDatabase.update(TABLE, p1, p2, p3)
                context!!.contentResolver.notifyChange(p0, null)
            }
            else -> {throw IllegalArgumentException("Unknown Uri: " + p0.toString())}
        }
        return rowId
    }
}
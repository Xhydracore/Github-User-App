package com.submission.githubuserapp.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.submission.githubuserapp.helper.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME


internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "githubUsers"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
                    "(${DatabaseContract.FavoriteColumns.USERNAME} TEXT NOT NULL, " +
                    "${DatabaseContract.FavoriteColumns.AVATAR_URL} TEXT NOT NULL, " +
                    "${DatabaseContract.FavoriteColumns.USER_ID} INTEGER PRIMARY KEY AUTOINCREMENT)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}
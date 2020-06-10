package com.submission.githubuserapp.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.submission.githubuserapp.helper.DatabaseContract.FavoriteColumns.Companion.USER_ID

class FavoriteHelper(applicationContext: Context) {
    private val databaseHelper: DatabaseHelper = DatabaseHelper(applicationContext)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = DatabaseContract.FavoriteColumns.TABLE_NAME
        private var INSTANCE: FavoriteHelper? = null

        fun getInstance(context: Context): FavoriteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoriteHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()
        if (database.isOpen) {
            database.close()
        }
    }

    fun queryAll(): Cursor =
        database.query(DATABASE_TABLE, null, null, null, null, null, "$USER_ID ASC", null)

    fun queryByUsername(userID: String): Cursor =
        database.query(DATABASE_TABLE, null, "$USER_ID = ?", arrayOf(userID), null, null, null)

    fun save(values: ContentValues?): Long = database.insert(DATABASE_TABLE, null, values)

    fun deleteByUsersname(userID: String): Int =
        database.delete(DATABASE_TABLE, "$USER_ID = $userID", null)
}
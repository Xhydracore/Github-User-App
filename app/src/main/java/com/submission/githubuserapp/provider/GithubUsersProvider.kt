package com.submission.githubuserapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.submission.githubuserapp.helper.DatabaseContract.AUTHORITY
import com.submission.githubuserapp.helper.DatabaseContract.FavoriteColumns.Companion.GITHUB_USERS_URI
import com.submission.githubuserapp.helper.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.submission.githubuserapp.helper.FavoriteHelper

class GithubUsersProvider : ContentProvider() {

    companion object{
        private const val GITHUB_USERS = 1
        private const val USERNAME_GITHUB_USER = 2
        private lateinit var githubUsersHelper: FavoriteHelper
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, GITHUB_USERS)
            uriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", USERNAME_GITHUB_USER)
        }
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val add: Long = when(GITHUB_USERS){
            uriMatcher.match(uri) -> githubUsersHelper.save(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(GITHUB_USERS_URI, null)

        Log.e("content-provider", "${values?.get("user_id")}")
        return Uri.parse("$GITHUB_USERS_URI/${values?.get("user_id")}")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        Log.e("query", "$uri")
        Log.e("uriMatch", "${uriMatcher.match(uri)}")
        when(uriMatcher.match(uri)){
            GITHUB_USERS -> cursor = githubUsersHelper.queryAll()
            USERNAME_GITHUB_USER -> cursor = githubUsersHelper.queryByUsername(uri.lastPathSegment.toString())
            else -> cursor = null
        }
        return cursor
    }

    override fun onCreate(): Boolean {
        githubUsersHelper = FavoriteHelper.getInstance(context as Context)
        githubUsersHelper.open()
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.e("delete", "${uri.lastPathSegment}")
        val deleted: Int = when(USERNAME_GITHUB_USER){
            uriMatcher.match(uri) -> githubUsersHelper.deleteByUsersname(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(GITHUB_USERS_URI, null)
        return deleted
    }

    override fun getType(uri: Uri): String? = null
}

package com.submission.githubuserapp.helper

import android.database.Cursor
import com.submission.githubuserapp.model.Favorite

object MappingHelper {
    fun mapCursorToArrayList(favoriteCursor: Cursor?) : ArrayList<Favorite> {
        val favoriteList = arrayListOf<Favorite>()

        favoriteCursor?.apply {
            while(moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USER_ID))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val avatar_url = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR_URL))
                favoriteList.add(Favorite(id=id,login=username,avatar_url=avatar_url))
            }
        }
        return favoriteList
    }

    fun mapCursorToObject(cursor: Cursor?): Favorite{
        var user = Favorite()
        cursor?.apply {
            moveToFirst()
            val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
            val avatar_url = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR_URL))
            val id = getLong(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USER_ID))
            user = Favorite(login = username, avatar_url = avatar_url, id = id)
        }
        return user
    }
}
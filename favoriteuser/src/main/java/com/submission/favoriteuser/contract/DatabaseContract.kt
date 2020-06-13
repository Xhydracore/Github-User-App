package com.submission.favoriteuser.contract

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.submission.githubuserapp"
    const val SCHEME = "content"

    class FavoriteColumns: BaseColumns {
        companion object{
            private const val TABLE_NAME = "github_users"
            const val USERNAME = "username"
            const val AVATAR_URL = "avatar_url"
            const val USER_ID = "user_id"

            val GITHUB_USERS_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}
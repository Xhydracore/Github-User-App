package com.submission.githubuserapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Favorite (
    var id: Long = 0,
    var login: String = "",
    var avatar_url: String = ""
): Parcelable
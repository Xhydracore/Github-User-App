package com.submission.githubuserapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var username: String = "",
    var name: String = "",
    var avatar: Int = 0,
    var company: String = "",
    var location: String = "",
    var repository: String = "",
    var follower: String = "",
    var following: String = ""
) : Parcelable
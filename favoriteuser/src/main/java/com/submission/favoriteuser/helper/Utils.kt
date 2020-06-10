package com.submission.favoriteuser.helper

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import java.util.*

object Utils {
    val dailyBroadcastID = 12

    fun loadImage(context: Context, avatarURL: String?): RequestBuilder<Drawable> {
        return Glide.with(context)
            .load(avatarURL)
            .apply(RequestOptions.circleCropTransform())
    }

    fun getCalendar(hour: Int, minute: Int, second: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, second)
        return calendar
    }
}
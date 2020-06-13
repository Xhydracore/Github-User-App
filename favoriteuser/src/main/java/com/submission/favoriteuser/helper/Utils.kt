package com.submission.favoriteuser.helper

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions

object Utils {

    fun loadImage(context: Context, avatarURL: String?): RequestBuilder<Drawable> {
        return Glide.with(context)
            .load(avatarURL)
            .apply(RequestOptions.circleCropTransform())
    }

}
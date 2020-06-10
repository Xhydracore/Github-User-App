package com.submission.githubuserapp.view

import com.submission.githubuserapp.model.DetailUserResponse

interface DetailUsernameView {
    fun onShowLoading()
    fun onHideLoading()
    fun onSuccessGetDetailUsername(response: DetailUserResponse?)
    fun onErrorGetDetailUsername(message: String)
}
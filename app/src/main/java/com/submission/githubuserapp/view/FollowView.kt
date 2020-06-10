package com.submission.githubuserapp.view

import com.submission.githubuserapp.model.FollowResponse

interface FollowView {
    fun onShowLoading()
    fun onHideLoading()
    fun onSuccessFollow(data: List<FollowResponse?>?)
    fun onErrorFollower(message: String)
}
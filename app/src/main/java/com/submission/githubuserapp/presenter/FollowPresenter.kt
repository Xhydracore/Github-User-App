package com.submission.githubuserapp.presenter

import com.submission.githubuserapp.model.FollowResponse
import com.submission.githubuserapp.network.NetworkConfig
import com.submission.githubuserapp.view.FollowView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowPresenter(val followView: FollowView) {

    fun getFollower(username: String?) {
        followView.onShowLoading()
        NetworkConfig.service()
            .getUserFollower(username.toString())
            .enqueue(object : Callback<List<FollowResponse>> {
                override fun onFailure(call: Call<List<FollowResponse>>, t: Throwable) {
                    followView.onHideLoading()
                    followView.onErrorFollower(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<List<FollowResponse>>,
                    response: Response<List<FollowResponse>>
                ) {
                    if (response.isSuccessful) {
                        followView.onHideLoading()
                        followView.onSuccessFollow(response.body())
                    }
                }

            })
    }

    fun getFollowing(username: String?) {
        followView.onShowLoading()
        NetworkConfig.service()
            .getUserFollowing(username.toString())
            .enqueue(object : Callback<List<FollowResponse>> {
                override fun onFailure(call: Call<List<FollowResponse>>, t: Throwable) {
                    followView.onHideLoading()
                    followView.onErrorFollower(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<List<FollowResponse>>,
                    response: Response<List<FollowResponse>>
                ) {
                    if (response.isSuccessful) {
                        followView.onHideLoading()
                        followView.onSuccessFollow(response.body())
                    }
                }

            })
    }
}
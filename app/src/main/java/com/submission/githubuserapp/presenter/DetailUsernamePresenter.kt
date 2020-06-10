package com.submission.githubuserapp.presenter

import com.submission.githubuserapp.model.DetailUserResponse
import com.submission.githubuserapp.network.NetworkConfig
import com.submission.githubuserapp.view.DetailUsernameView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUsernamePresenter(val detailUsernameView: DetailUsernameView) {
    fun getDetailUsername(username: String?) {
        detailUsernameView.onShowLoading()
        NetworkConfig.service()
            .getDetailUser(username.toString())
            .enqueue(object: Callback<DetailUserResponse> {
                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    detailUsernameView.onErrorGetDetailUsername(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        detailUsernameView.onHideLoading()
                        detailUsernameView.onSuccessGetDetailUsername(response.body())
                    }
                }

            })
    }
}
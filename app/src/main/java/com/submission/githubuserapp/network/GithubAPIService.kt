package com.submission.githubuserapp.network

import com.submission.githubuserapp.model.DetailUserResponse
import com.submission.githubuserapp.model.FollowResponse
import com.submission.githubuserapp.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPIService {
    @Headers("Authorization: token b428e7a47f8db033452b768f7a489f3d4012b8e0")
    @GET("search/users?")
    fun getSearchUsers(
        @Query("q") q : String
    ) : Call<SearchResponse>

    @Headers("Authorization: token b428e7a47f8db033452b768f7a489f3d4012b8e0")
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username : String
    ) : Call<DetailUserResponse>

    @Headers("Authorization: token b428e7a47f8db033452b768f7a489f3d4012b8e0")
    @GET("users/{username}/followers")
    fun getUserFollower(
        @Path("username") username : String
    ) : Call<List<FollowResponse>>

    @Headers("Authorization: token b428e7a47f8db033452b768f7a489f3d4012b8e0")
    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ) : Call<List<FollowResponse>>
}
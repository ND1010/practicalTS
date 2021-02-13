package com.dhruv.apps.historyapp.network

import com.dhruv.apps.historyapp.resmodel.UserFeedResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("users")
    fun getUser(
        @Query("offset") per_page: String,
        @Query("limit") since: String
    ): Call<UserFeedResponse>
}
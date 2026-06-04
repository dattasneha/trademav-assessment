package com.snehadatta.data.remote

import com.snehadatta.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("posts")
    suspend fun getPosts(
        @Query("_limit") limit: Int = 10
    ): List<Post>
}
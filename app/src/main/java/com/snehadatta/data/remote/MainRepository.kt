package com.snehadatta.data.remote

import com.snehadatta.data.model.Post
import com.snehadatta.signalviewer.util.Resource

class MainRepository(
    private val api: ApiService
) {
    suspend fun getPosts(
        limit: Int
    ): Resource<List<Post>> {

        return try {

            Resource.Success(
                api.getPosts(limit)
            )

        } catch (e: Exception) {

            Resource.Error(
                e.message ?: "Something went wrong"
            )
        }
    }
}
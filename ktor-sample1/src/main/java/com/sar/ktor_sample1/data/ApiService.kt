package com.sar.ktor_sample1.data

import com.sar.ktor_sample1.model.Post
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ApiService(private val client : HttpClient = apiClient) {

    suspend fun getPosts() = runCatching {
        client.get(BASE_URL_POSTS).body<List<Post>>()
    }

    companion object {
        private const val BASE_URL_POSTS = "https://jsonplaceholder.typicode.com/posts"
    }
}
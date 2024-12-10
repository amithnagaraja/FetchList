package com.app.fetchlist.network

import com.app.fetchlist.model.Item
import retrofit2.http.GET

interface ListApiService {
    @GET("hiring.json")
    suspend fun fetchList(): List<Item>
}
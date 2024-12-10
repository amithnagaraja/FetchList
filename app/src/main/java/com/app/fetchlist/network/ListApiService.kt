package com.app.fetchlist.network

import com.app.fetchlist.model.Item
import retrofit2.http.GET

interface ListApiService {
    @GET
    suspend fun fetchList(): List<Item>
}
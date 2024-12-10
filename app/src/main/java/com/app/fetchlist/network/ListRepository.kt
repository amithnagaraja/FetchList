package com.app.fetchlist.network

import com.app.fetchlist.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ListRepository(
    private val apiService: ListApiService
) {
    fun getListItems(): Flow<List<Item>> = flow {
        try {
            val list = apiService.fetchList()
            emit(list.filter { !it.name.isNullOrBlank() }
                .sortedWith(compareBy( {it.listId}, {it.name})))
        } catch (exception: Exception) {
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)
}
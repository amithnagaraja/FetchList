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
            //filter list that has empty name, sorting first by listId followed by name and emit data
            emit(list.filter { !it.name.isNullOrBlank() }
                .sortedWith(compareBy( {it.listId}, {it.name})))
        } catch (exception: Exception) {
            //emit empty list when there is an exception
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)
}
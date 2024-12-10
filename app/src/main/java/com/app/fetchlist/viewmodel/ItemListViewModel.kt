package com.app.fetchlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.fetchlist.model.Item
import com.app.fetchlist.network.ListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class ItemListViewModel(
    private val repository: ListRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items : StateFlow<List<Item>> = _items

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchItems() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.getListItems().take(1)
                    .collect {fetchItems ->
                        _items.value = fetchItems
                    }
            } catch (exception: Exception) {
                //on error, emit empty list
                _items.value = emptyList()
            } finally {
                //set loading value to be false
                _isLoading.value = false
            }
        }
    }
}
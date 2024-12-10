package com.app.fetchlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.fetchlist.network.ListRepository

class ItemListViewModelFactory(
    private val repository: ListRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ItemListViewModel::class.java)) {
            return ItemListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
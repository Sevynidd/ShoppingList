package com.shoppinglist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shoppinglist.roomDatabase.Item
import com.shoppinglist.roomDatabase.List
import kotlinx.coroutines.launch

class RoomViewModel(private val repository: RoomRepository) : ViewModel() {
    fun getLists() = repository.getAllLists().asLiveData(viewModelScope.coroutineContext)

    fun upsertList(list: List) {
        viewModelScope.launch {
            repository.upsertList(list)
        }
    }

    fun deleteList(list: List) {
        viewModelScope.launch {
            repository.deleteList(list)
        }
    }

    fun getItems() = repository.getAllItems().asLiveData(viewModelScope.coroutineContext)

    fun upsertItem(item: Item) {
        viewModelScope.launch {
            repository.upsertItem(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }
}
package com.shoppinglist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shoppinglist.roomDatabase.RoomItem
import com.shoppinglist.roomDatabase.RoomList
import kotlinx.coroutines.launch

class RoomViewModel(private val repository: RoomRepository) : ViewModel() {
    fun getLists() = repository.getAllLists().asLiveData(viewModelScope.coroutineContext)

    fun upsertList(list: RoomList) {
        viewModelScope.launch {
            repository.upsertList(list)
        }
    }

    fun deleteList(list: RoomList) {
        viewModelScope.launch {
            repository.deleteList(list)
        }
    }

    fun getItems() = repository.getAllItems().asLiveData(viewModelScope.coroutineContext)

    fun upsertItem(item: RoomItem) {
        viewModelScope.launch {
            repository.upsertItem(item)
        }
    }

    fun deleteItem(item: RoomItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

}
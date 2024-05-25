package com.shoppinglist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppinglist.roomDatabase.entities.ListWithItemCount
import com.shoppinglist.roomDatabase.entities.RoomItem
import com.shoppinglist.roomDatabase.entities.RoomList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.Thread.State

class RoomViewModel(private val repository: RoomRepository) : ViewModel() {
    val allLists: StateFlow<List<ListWithItemCount>> =
        repository.getAllLists().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

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

    val allItems: StateFlow<List<RoomItem>> =
        repository.getAllItems().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

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
package com.shoppinglist.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppinglist.roomDatabase.entities.ListWithItemCount
import com.shoppinglist.roomDatabase.entities.RoomItem
import com.shoppinglist.roomDatabase.entities.RoomList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
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

    private val _listID = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val allItems: StateFlow<List<RoomItem>> = _listID.filterNotNull().flatMapLatest { listID ->
        repository.getAllItems(listID).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun setListID(listID: Int) {
        _listID.value = listID
    }

    private val _listFromListID = MutableStateFlow<RoomList?>(null)
    val listFromListID: StateFlow<RoomList?> get() = _listFromListID

    fun getListFromListID(listID: Int) {
        viewModelScope.launch {
            repository.getListFromListID(listID).collect { listEntry ->
                _listFromListID.value = listEntry
            }
        }
    }

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
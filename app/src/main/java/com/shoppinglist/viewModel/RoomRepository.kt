package com.shoppinglist.viewModel

import com.shoppinglist.roomDatabase.entities.RoomItem
import com.shoppinglist.roomDatabase.entities.RoomList
import com.shoppinglist.roomDatabase.RoomDatabase

class RoomRepository(private val db: RoomDatabase) {
    suspend fun upsertList(list: RoomList) {
        db.dao.upsertList(list)
    }

    suspend fun deleteList(list: RoomList) {
        db.dao.deleteList(list)
    }

    fun getAllLists() = db.dao.getAllLists()

    suspend fun upsertItem(item: RoomItem) {
        db.dao.upsertItem(item)
    }

    suspend fun deleteItem(item: RoomItem) {
        db.dao.deleteItem(item)
    }

    fun getAllItems() = db.dao.getAllItems()

}
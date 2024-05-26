package com.shoppinglist.viewModel

import com.shoppinglist.roomDatabase.entities.RoomItem
import com.shoppinglist.roomDatabase.entities.RoomList
import com.shoppinglist.roomDatabase.RoomDatabase
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val db: RoomDatabase) {
    suspend fun upsertList(list: RoomList) {
        db.dao.upsertList(list)
    }

    suspend fun deleteList(list: RoomList) {
        db.dao.deleteList(list)
    }

    fun getAllLists() = db.dao.getAllLists()

    fun getListFromListID(listID: Int) = db.dao.getListFromListID(listID = listID)

    suspend fun upsertItem(item: RoomItem) {
        db.dao.upsertItem(item)
    }

    suspend fun deleteItem(item: RoomItem) {
        db.dao.deleteItem(item)
    }

    fun getAllItems(listID: Int) = db.dao.getAllItems(listID = listID)

}
package com.shoppinglist.viewModel

import com.shoppinglist.roomDatabase.List
import com.shoppinglist.roomDatabase.RoomDatabase

class RoomRepository(private val db: RoomDatabase) {
    suspend fun upsertList(list: List) {
        db.dao.upsertList(list)
    }

    suspend fun deleteList(list: List) {
        db.dao.deleteList(list)
    }

    fun getAllLists() = db.dao.getAllLists()
}
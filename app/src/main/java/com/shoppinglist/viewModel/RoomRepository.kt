package com.shoppinglist.viewModel

import com.shoppinglist.roomDatabase.RoomDao
import com.shoppinglist.roomDatabase.entities.RoomItem
import com.shoppinglist.roomDatabase.entities.RoomList
import com.shoppinglist.roomDatabase.RoomDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomRepository @Inject constructor(private val dao: RoomDao) {

    // region List
    suspend fun upsertList(list: RoomList) {
        dao.upsertList(list)
    }

    suspend fun deleteList(list: RoomList) {
        dao.deleteList(list)
    }

    fun getAllLists() = dao.getAllLists()

    fun getListFromListID(listID: Int) = dao.getListFromListID(listID = listID)

    fun getListFromListIDAndItemsSum(listID: Int) = dao.getListFromListIDAndItemsPrice(listID = listID)

    // endregion

    // region Item
    suspend fun upsertItem(item: RoomItem) {
        dao.upsertItem(item)
    }

    suspend fun deleteItem(item: RoomItem) {
        dao.deleteItem(item)
    }

    fun getAllItems(listID: Int) = dao.getAllItems(listID = listID)

    fun getItemFromItemID(itemID: Int) = dao.getItemFromItemID(itemID = itemID)

    // endregion

}
package com.shoppinglist.roomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Upsert
    suspend fun upsertList(list: RoomList)

    @Delete
    suspend fun deleteList(list: RoomList)

    @Query("SELECT l.*, COUNT(i.listID) as itemCount FROM List l LEFT JOIN Item i ON l.listID = i.listID")
    fun getAllLists(): Flow<List<ListWithItemCount>>

    @Upsert
    suspend fun upsertItem(item: RoomItem)

    @Delete
    suspend fun deleteItem(item: RoomItem)

    @Query("SELECT * FROM Item")
    fun getAllItems(): Flow<List<RoomItem>>

}
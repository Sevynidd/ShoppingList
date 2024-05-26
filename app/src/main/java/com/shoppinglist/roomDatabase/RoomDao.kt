package com.shoppinglist.roomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.shoppinglist.roomDatabase.entities.ListWithItemCount
import com.shoppinglist.roomDatabase.entities.RoomItem
import com.shoppinglist.roomDatabase.entities.RoomList
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Upsert
    suspend fun upsertList(list: RoomList)

    @Delete
    suspend fun deleteList(list: RoomList)

    @Query("SELECT l.*, COUNT(i.listID) as itemCount FROM List l LEFT JOIN Item i ON l.listID = i.listID GROUP BY l.listID")
    fun getAllLists(): Flow<List<ListWithItemCount>>

    @Query("SELECT * FROM List WHERE listID = :listID")
    fun getListFromListID(listID: Int): Flow<RoomList>

    @Upsert
    suspend fun upsertItem(item: RoomItem)

    @Delete
    suspend fun deleteItem(item: RoomItem)

    @Query("SELECT * FROM Item WHERE listID = :listID")
    fun getAllItems(listID: Int): Flow<List<RoomItem>>

}
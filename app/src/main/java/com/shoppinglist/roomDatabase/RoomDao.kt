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
    suspend fun upsertList(list: List)
    @Delete
    suspend fun deleteList(list: List)

    @Query("SELECT * FROM List")
    fun getAllLists(): Flow<kotlin.collections.List<List>>

    @Upsert
    suspend fun upsertItem(item: Item)
    @Delete
    suspend fun deleteItem(item: Item)

    @Query("SELECT * FROM Item")
    fun getAllItems(): Flow<kotlin.collections.List<Item>>
}
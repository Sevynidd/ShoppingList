package com.shoppinglist.roomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
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
}
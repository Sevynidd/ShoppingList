package com.shoppinglist.roomDatabase

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "List")
data class RoomList(
    @PrimaryKey(autoGenerate = true)
    val listID: Int = 0,
    val name: String,
    val note: String
)

package com.shoppinglist.roomDatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "List")
data class RoomList(
    @PrimaryKey(autoGenerate = true)
    val listID: Int = 0,
    val name: String,
    val note: String,
    val notifyDate: Long?
)

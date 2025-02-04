package com.shoppinglist.roomDatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Tag"
)
data class RoomTag(
    @PrimaryKey(autoGenerate = true)
    val tagID: Int = 0,
    val name: String,
    val color: String
)

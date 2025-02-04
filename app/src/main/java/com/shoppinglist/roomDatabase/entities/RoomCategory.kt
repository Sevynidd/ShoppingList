package com.shoppinglist.roomDatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Category"
)
data class RoomCategory(
    @PrimaryKey(autoGenerate = true)
    val categoryID: Int = 0,
    val name: String
)

package com.shoppinglist.roomDatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Unit"
)
data class RoomUnit(
    @PrimaryKey(autoGenerate = true)
    val unitID: Int = 0,
    val name: String
)

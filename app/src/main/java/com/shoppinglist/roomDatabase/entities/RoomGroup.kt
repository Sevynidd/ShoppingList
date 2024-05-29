package com.shoppinglist.roomDatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Group"
)
data class RoomGroup(
    @PrimaryKey(autoGenerate = true)
    val groupID: Int = 0,
    val name: String
)

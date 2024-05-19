package com.shoppinglist.roomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Item"
)
data class Item(
    @PrimaryKey(autoGenerate = true)
    val itemID: Int = 0,
    val price: Double?,
    val name: String,
    val note: String?,
    val amount: Int?
)

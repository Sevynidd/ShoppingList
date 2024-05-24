package com.shoppinglist.roomDatabase

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Item",
    foreignKeys = [ForeignKey(
        entity = RoomList::class,
        parentColumns = ["listID"],
        childColumns = ["listID"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RoomItem(
    @PrimaryKey(autoGenerate = true)
    val itemID: Int = 0,
    val price: Double?,
    val name: String,
    val note: String?,
    val amount: Int?,
    val listID: Int
)

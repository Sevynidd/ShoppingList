package com.shoppinglist.roomDatabase.entities

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
    ),
    ForeignKey(
        entity = RoomCategory::class,
        parentColumns = ["categoryID"],
        childColumns = ["categoryID"],
        onDelete = ForeignKey.SET_NULL
    ),
    ForeignKey(
        entity = RoomGroup::class,
        parentColumns = ["groupID"],
        childColumns = ["groupID"],
        onDelete = ForeignKey.SET_NULL
    )]
)
data class RoomItem(
    @PrimaryKey(autoGenerate = true)
    val itemID: Int = 0,
    val price: Float = 0.0F,
    val name: String,
    val note: String?,
    val amount: Int = 1,
    val isChecked: Boolean = false,
    val listID: Int,
    val categoryID: Int?,
    val groupID: Int?
)

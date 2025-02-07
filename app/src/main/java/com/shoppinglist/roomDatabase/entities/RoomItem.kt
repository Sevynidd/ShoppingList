package com.shoppinglist.roomDatabase.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.net.URL

@Entity(
    tableName = "Item", foreignKeys = [ForeignKey(
        entity = RoomList::class,
        parentColumns = ["listID"],
        childColumns = ["listID"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = RoomTag::class,
        parentColumns = ["tagID"],
        childColumns = ["tagID"],
        onDelete = ForeignKey.SET_NULL
    ), ForeignKey(
        entity = RoomCategory::class,
        parentColumns = ["categoryID"],
        childColumns = ["categoryID"],
        onDelete = ForeignKey.SET_NULL
    )], indices = [Index(
        value = ["listID"]
    ), Index(
        value = ["tagID"]
    ), Index(
        value = ["categoryID"]
    )]
)
data class RoomItem(
    @PrimaryKey(autoGenerate = true) val itemID: Int = 0,
    val price: Float = 0.0F,
    val name: String,
    val note: String? = null,
    val amount: Int = 1,
    val isChecked: Boolean = false,
    val url: URL? = null,
    val listID: Int,
    val tagID: Int? = null,
    val categoryID: Int? = null
)

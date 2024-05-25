package com.shoppinglist.roomDatabase.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Category",
    foreignKeys = [ForeignKey(
        entity = RoomAddress::class,
        parentColumns = ["addressID"],
        childColumns = ["addressID"],
        onDelete = ForeignKey.SET_NULL
    )]
    )
data class RoomCategory(
    @PrimaryKey(autoGenerate = true)
    val categoryID: Int = 0,
    val name: String,
    val url: String,
    val addressID: Int?
)

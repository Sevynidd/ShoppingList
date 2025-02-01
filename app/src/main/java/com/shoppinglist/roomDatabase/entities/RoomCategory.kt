package com.shoppinglist.roomDatabase.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Category",
    foreignKeys = [ForeignKey(
        entity = RoomAddress::class,
        parentColumns = ["addressID"],
        childColumns = ["addressID"],
        onDelete = ForeignKey.SET_NULL
    )],
    indices = [Index(
        value = ["addressID"]
    )]
)
data class RoomCategory(
    @PrimaryKey(autoGenerate = true)
    val categoryID: Int = 0,
    val name: String,
    val url: String,
    val color: String,
    val addressID: Int?
)

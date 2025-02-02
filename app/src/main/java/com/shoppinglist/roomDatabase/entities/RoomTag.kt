package com.shoppinglist.roomDatabase.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Tag",
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
data class RoomTag(
    @PrimaryKey(autoGenerate = true)
    val tagID: Int = 0,
    val name: String,
    val color: String,
    val addressID: Int?
)

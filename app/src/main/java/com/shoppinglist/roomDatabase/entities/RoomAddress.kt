package com.shoppinglist.roomDatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Address"
)
data class RoomAddress(
    @PrimaryKey(autoGenerate = true)
    val addressID: Int = 0,
    val street: String,
    val houseNumber: String,
    val postalCode: String,
    val city: String
)

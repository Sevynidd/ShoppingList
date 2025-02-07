package com.shoppinglist.roomDatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Address"
)
data class RoomAddress (
    @PrimaryKey(autoGenerate = true)
    val addressID: Int = 0,
    val street: String,
    val nr: Int? = null,
    val nrAddition: String? = null,
    val postalCode: Int,
    val city: String
)

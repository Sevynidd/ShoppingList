package com.shoppinglist.roomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class List(
    @PrimaryKey(autoGenerate = true)
    val listID: Int = 0,
    val name: String,
    val note: String
)

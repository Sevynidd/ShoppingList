package com.shoppinglist.roomDatabase.entities

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "List")
data class RoomList(
    @PrimaryKey(autoGenerate = true)
    val listID: Int = 0,
    val name: String,
    val note: String,
    val notifyDate: Long? = null,
    val icon: ImageVector? = Icons.Default.ShoppingCart
)

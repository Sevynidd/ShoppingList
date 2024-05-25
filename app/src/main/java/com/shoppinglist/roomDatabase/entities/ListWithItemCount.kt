package com.shoppinglist.roomDatabase.entities

import androidx.room.Embedded

data class ListWithItemCount(
    @Embedded
    val list: RoomList,
    val itemCount: Int
)

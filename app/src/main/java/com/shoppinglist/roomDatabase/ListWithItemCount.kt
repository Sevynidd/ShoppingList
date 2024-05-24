package com.shoppinglist.roomDatabase

import androidx.room.Embedded

data class ListWithItemCount(
    @Embedded
    val list: RoomList?,
    val itemCount: Int?
)

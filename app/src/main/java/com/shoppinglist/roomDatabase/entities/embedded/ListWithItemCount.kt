package com.shoppinglist.roomDatabase.entities.embedded

import androidx.room.Embedded
import com.shoppinglist.roomDatabase.entities.RoomList

data class ListWithItemCount(
    @Embedded
    val list: RoomList,
    val itemCount: Int
)

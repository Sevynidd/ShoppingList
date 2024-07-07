package com.shoppinglist.roomDatabase.entities.embedded

import androidx.room.Embedded
import com.shoppinglist.roomDatabase.entities.RoomList

data class ListWithPriceSum(
    @Embedded
    val list: RoomList,
    val sumPrice: Float
)

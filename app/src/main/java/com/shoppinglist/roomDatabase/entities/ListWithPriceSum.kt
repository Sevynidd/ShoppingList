package com.shoppinglist.roomDatabase.entities

import androidx.room.Embedded

data class ListWithPriceSum(
    @Embedded
    val list: RoomList,
    val sumPrice: Float
)

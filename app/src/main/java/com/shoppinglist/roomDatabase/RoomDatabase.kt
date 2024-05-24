package com.shoppinglist.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RoomList::class, RoomItem::class],
    version = 1
)
abstract class RoomDatabase: RoomDatabase() {
    abstract val dao: RoomDao
}
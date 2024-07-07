package com.shoppinglist.roomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shoppinglist.roomDatabase.entities.RoomAddress
import com.shoppinglist.roomDatabase.entities.RoomCategory
import com.shoppinglist.roomDatabase.entities.RoomGroup
import com.shoppinglist.roomDatabase.entities.RoomItem
import com.shoppinglist.roomDatabase.entities.RoomList

@Database(
    entities = [RoomList::class,
        RoomItem::class,
        RoomCategory::class,
        RoomAddress::class,
        RoomGroup::class],
    exportSchema = false,
    version = 1
)
abstract class RoomDatabase : RoomDatabase() {
    abstract val dao: RoomDao
}
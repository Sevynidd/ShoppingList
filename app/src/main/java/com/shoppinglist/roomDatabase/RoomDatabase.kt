package com.shoppinglist.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shoppinglist.roomDatabase.entities.RoomCategory
import com.shoppinglist.roomDatabase.entities.RoomItem
import com.shoppinglist.roomDatabase.entities.RoomList
import com.shoppinglist.roomDatabase.entities.RoomTag

@Database(
    entities = [RoomList::class, RoomItem::class, RoomTag::class, RoomCategory::class],
    exportSchema = false,
    version = 1
)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun roomDao(): RoomDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabase? = null

        fun getInstance(context: Context): RoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    com.shoppinglist.roomDatabase.RoomDatabase::class.java,
                    "ShoppingList.db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
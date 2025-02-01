package com.shoppinglist

import android.content.Context
import androidx.room.Room
import com.shoppinglist.roomDatabase.RoomDao
import com.shoppinglist.roomDatabase.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RoomDatabase {
        return Room.databaseBuilder(
            context,
            RoomDatabase::class.java,
            "ShoppingList.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideRoomDao(db: RoomDatabase): RoomDao {
        return db.roomDao()
    }
}
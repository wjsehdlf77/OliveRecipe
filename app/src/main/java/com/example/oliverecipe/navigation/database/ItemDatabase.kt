package com.example.oliverecipe.navigation.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.oliverecipe.navigation.model.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemDatabase: RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object{
        @Volatile // 다른 스레드에서 접근 가능
        private var INSTANCE: ItemDatabase? = null

        fun getDatabase(context: Context):ItemDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){ // 새 데이터베이스 인스턴스
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java,
                    "item_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
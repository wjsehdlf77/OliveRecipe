package com.example.oliverecipe.navigation.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.oliverecipe.navigation.model.Item


@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addItem(item: Item)

    @Update
    fun updateItem(item:Item)

    @Delete
    fun deleteItem(item:Item)

    @Query("DELETE FROM item_table")
    fun deleteAllItems()

    @Query("SELECT * FROM item_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Item>>
}
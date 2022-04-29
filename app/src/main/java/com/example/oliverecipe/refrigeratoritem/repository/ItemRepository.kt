package com.example.oliverecipe.refrigeratoritem.repository

import androidx.lifecycle.LiveData
import com.example.oliverecipe.navigation.database.ItemDao
import com.example.oliverecipe.navigation.model.Item

class ItemRepository(private val itemDao: ItemDao) {
    val readAllData: LiveData<List<Item>> = itemDao.readAllData()
    val recentName: LiveData<List<Item>> = itemDao.recentName()

    fun addItem(item: Item?){
        itemDao.addItem(item)
    }

    fun updateItem(item:Item){
        itemDao.updateItem(item)
    }

    fun deleteItem(item:Item) {
        itemDao.deleteItem(item)
    }

    fun deleteAllItems() {
        itemDao.deleteAllItems()
    }

}
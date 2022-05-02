package com.example.oliverecipe.navigation.database

import androidx.room.Database
import androidx.room.RoomDatabase



@Database(entities = [OliveRecipe::class], version = 1)
abstract class OliveRecipeDatabase: RoomDatabase() {
    abstract fun oliveRecipeDao(): OliveRecipeDao

}
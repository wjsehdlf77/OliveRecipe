package com.example.oliverecipe.navigation.database


import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE



@Dao
interface OliveRecipeDao {

    @Query("SELECT * FROM oliveRecipe")
    fun getAll(): List<OliveRecipe>

    @Insert(onConflict  = REPLACE)
    fun insert(oliveRecipe: OliveRecipe)

    @Delete
    fun delete(oliveRecipe: OliveRecipe)

}


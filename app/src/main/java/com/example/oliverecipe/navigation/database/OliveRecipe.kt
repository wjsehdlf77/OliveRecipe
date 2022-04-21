package com.example.oliverecipe.navigation.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "oliveRecipe")
class OliveRecipe {
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo
    var id: Long? = null

    @ColumnInfo
    var content: String = ""


    constructor(content: String){
        this.content = content
    }
}



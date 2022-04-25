package com.example.oliverecipe.navigation.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "item_table")
data class Item(
    @PrimaryKey(autoGenerate = true) //primary key 자동
    val id:Int,
    val itemName:String,
    val itemProperty:String,
    val validity:Int
): Parcelable {

}
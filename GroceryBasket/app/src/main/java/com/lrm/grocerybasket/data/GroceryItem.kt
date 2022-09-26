package com.lrm.grocerybasket.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery_items")
data class GroceryItem (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val itemName: String,
    @ColumnInfo(name = "quantity")
    val itemQuantity: Int,
    @ColumnInfo(name = "price")
    val itemPrice: Int
    )

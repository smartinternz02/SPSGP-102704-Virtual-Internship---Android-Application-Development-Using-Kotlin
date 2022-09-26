package com.lrm.grocerybasket

import android.app.Application
import com.lrm.grocerybasket.data.ItemRoomDatabase

class GroceryBasketApplication: Application() {
    val database: ItemRoomDatabase by lazy {ItemRoomDatabase.getDatabase(this)}
}
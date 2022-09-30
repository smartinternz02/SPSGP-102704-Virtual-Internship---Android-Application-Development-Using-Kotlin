package com.lrm.grocerybasket

import androidx.lifecycle.*
import com.lrm.grocerybasket.data.GroceryDao
import com.lrm.grocerybasket.data.GroceryItem
import kotlinx.coroutines.launch

class GroceryViewModel(private val groceryDao: GroceryDao): ViewModel() {

    val allItems: LiveData<List<GroceryItem>> = groceryDao.getItems().asLiveData()

    //Launching a new coroutine to insert an item in a non-blocking way
    private fun insertItem(item: GroceryItem) {
        viewModelScope.launch {
            groceryDao.insert(item)
        }
    }

    private fun getNewItemEntry(itemName: String, itemQuantity: String, itemPrice: String): GroceryItem {
        return GroceryItem(
            itemName = itemName,
            itemQuantity = itemQuantity.toInt(),
            itemPrice = itemPrice.toInt()
        )
    }

    //Inserts the new Item into database.
    fun addNewItem(itemName: String, itemQuantity: String, itemPrice: String) {
        val newItem = getNewItemEntry(itemName, itemQuantity, itemPrice)
        insertItem(newItem)
    }

    //Returns true if the EditTexts are not empty
    fun isEntryValid(itemName: String, itemQuantity: String, itemPrice: String): Boolean {
        if(itemName.isBlank() || itemQuantity.isBlank() || itemPrice.isBlank()) {
            return false
        }
        return true
    }

    fun retrieveItem(id: Int): LiveData<GroceryItem> {
        return groceryDao.getItem(id).asLiveData()
    }

    private fun updateItem(item: GroceryItem) {
        viewModelScope.launch {
            groceryDao.update(item)
        }
    }

    fun deleteItem(item: GroceryItem) {
        viewModelScope.launch {
            groceryDao.delete(item)
        }
    }

    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemQuantity: String,
        itemPrice: String
    ): GroceryItem {
        return GroceryItem(
            id = itemId,
            itemName = itemName,
            itemQuantity = itemQuantity.toInt(),
            itemPrice = itemPrice.toInt()
        )
    }

    fun updateItem(
        itemId: Int,
        itemName: String,
        itemQuantity: String,
        itemPrice: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId,itemName, itemQuantity, itemPrice)
        updateItem(updatedItem)
    }

    fun deleteAllItems() {
        viewModelScope.launch {
            groceryDao.deleteAll()
        }
    }

}

class GroceryViewModelFactory(private val groceryDao: GroceryDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GroceryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GroceryViewModel(groceryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
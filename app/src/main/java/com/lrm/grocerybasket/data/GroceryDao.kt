package com.lrm.grocerybasket.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GroceryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: GroceryItem)

    @Update
    suspend fun update(item: GroceryItem)

    @Delete
    suspend fun delete(item: GroceryItem)

    @Query("SELECT * from grocery_items WHERE id = :id")
    fun getItem(id: Int): Flow<GroceryItem>

    @Query("SELECT * from grocery_items ORDER BY id ASC")
    fun getItems(): Flow<List<GroceryItem>>

    @Query("DELETE FROM grocery_items")
    suspend fun deleteAll()
}
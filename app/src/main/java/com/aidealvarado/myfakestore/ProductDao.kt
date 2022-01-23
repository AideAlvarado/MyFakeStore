package com.aidealvarado.myfakestore

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


@Dao
interface ProductDao {


    @Query("Select * from products")
    fun allProducts():LiveData<MutableList<Products>>

    @Query("Select * from products where NOT amount ='0'")
    fun getCart():LiveData<MutableList<Products>>


    @Query("Select * from products where NOT amount ='0'")
    fun getShoppingCart():List<Products>

    @Delete
    suspend fun delete(products: Products)
    @Query("Select * from products")
    fun getAllProducts(): LiveData<List<Products>>
    @Query("Select exists(Select * from products where id=:Id)")
    suspend fun checkId(Id: Int):Int
    @Insert(onConflict= REPLACE)
    suspend fun upsert(products: Products)
    @Insert(onConflict = REPLACE)
    suspend fun insertAll(products: MutableList<Products>)
    @Query("Select * from products")
    fun cartProducts():MutableList<Products>


}

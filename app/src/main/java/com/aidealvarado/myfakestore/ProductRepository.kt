package com.aidealvarado.myfakestore

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import org.json.JSONObject

private val TAG=ProductRepository::class.java.simpleName
class ProductRepository (private val database:ProductsDb) {
    val prodList:LiveData<MutableList<Products>> = database.productDao.allProducts()
    val cartList:LiveData<MutableList<Products>> = database.productDao.getCart()

    suspend fun fetchProducts(){
        Log.d(TAG,"Fetching products")
        return withContext(Dispatchers.IO){
            val prodArray = service.getAllProducts()
            /*Log.d(TAG,"$prodArray")
            val gson = Gson()
            val strArray = gson.fromJson(prodArray, mutableListOf<Products>())
            val prodList = parseProdResultString(prodArray)
            */
            //parseProdResult(prodArray)

            database.productDao.insertAll(parseProdResult(prodArray))

        }
    }

    private fun parseProdResultString(prodArray: String):MutableList<Products>{

        Log.d(TAG, prodArray)


        val _eqList = mutableListOf<Products>()

        return  _eqList
    }

    private fun parseProdResult(prodArray: ProdJsonResponse): MutableList<Products> {


            val eqList = mutableListOf<Products>()
            val productos = prodArray.results
            for(producto in productos){
                val id = producto.id
                val category = producto.category
                val description = producto.description
                val image = producto.image
                val price = producto.price
                val title = producto.title
                val amount = 0.toString()
                val prod = Products(id,category,description,image,price,title,amount)
                Log.d(TAG,"parseProdResult item $id , $description")
                eqList.add(prod)
        }
        return eqList
    }
    suspend fun checkId(Id:Int)= database.productDao.checkId(Id)
    suspend fun upsert(products: Products) = database.productDao.upsert(products)
    suspend fun calcTotal():String {
        val carList = database.productDao.cartProducts()
        var subtotal = 0.toBigDecimal()
        for (item in carList){
            val numberItems   =  item.amount!!.toBigDecimal()
            val unitPrice = item.price!!.toBigDecimal()
            subtotal += numberItems * unitPrice
        }
        return subtotal.toString()
    }

    suspend fun shoppingList():List<Products> = database.productDao.getShoppingCart()

}
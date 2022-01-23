package com.aidealvarado.myfakestore

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.UnknownHostException

private val TAG= MainViewModel::class.java.simpleName
class MainViewModel(application: Application):AndroidViewModel(application) {
    private val database = getDatabase(application.applicationContext)
    private val repository = ProductRepository(database)
    val eqList = repository.prodList
    init{
        viewModelScope.launch {
            try {
                Config.miCartList.clear()
                repository.fetchProducts()
            } catch (e:UnknownHostException){
                Log.d(TAG,"No internet Connection")
            }
        }
    }
    fun checkId(Id:Int) = viewModelScope.async {
        Log.d(TAG, "Chequeando el valor $Id")
        repository.checkId(Id)
    }
    fun upsert(products: Products) = viewModelScope.async {
        Log.d(TAG,"Actualizando el valor de ${products.id} a ${products.amount}")
        repository.upsert(products)
    }
    suspend fun shoppingList() = repository.shoppingList()
}
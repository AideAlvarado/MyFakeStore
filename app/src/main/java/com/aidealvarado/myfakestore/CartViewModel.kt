package com.aidealvarado.myfakestore

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.net.UnknownHostException

private val TAG= CartViewModel::class.java.simpleName
class CartViewModel(application: Application): AndroidViewModel(application) {
    private val database = getDatabase(application.applicationContext)
    private val repository = ProductRepository(database)

    lateinit var miCartList : LiveData<MutableList<Products>>
    lateinit var subtotal : String

    init {
        Log.d(TAG,"Init de la clase")
        viewModelScope.launch {
            try {
                Log.d(TAG,"Inicializando variables")
                miCartList = repository.cartList
                Log.d(TAG,"miCartList Inicializado ")
                subtotal = calcTotal()
                Log.d(TAG,"s Inicializado ")
            } catch (e:UnknownError) {
                Log.d(TAG,"Exception ${e.toString()}")
            }
        }
    }
    suspend fun calcTotal():String{
        return repository.calcTotal()
    }

}
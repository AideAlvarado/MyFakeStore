package com.aidealvarado.myfakestore

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private val TAG ="ProductsDb"
@Database(entities = [Products::class], version = 1, exportSchema = false)
abstract class ProductsDb: RoomDatabase() {
    // Lo definimos aqui, pero se usan en el Repo
    abstract val productDao:ProductDao
}


//Esto es estandar, para implementar in singleton de acceso a la base de datos.
private lateinit var INSTANCE: ProductsDb

fun getDatabase(context: Context): ProductsDb {
    synchronized(ProductsDb::class.java) {
        if (!::INSTANCE.isInitialized) {
            Log.d("TAG","Initializing database")
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ProductsDb::class.java,
                "productos"
            ).build()
        }
        return INSTANCE
    }
}
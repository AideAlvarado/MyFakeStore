package com.aidealvarado.myfakestore


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ProdApiService {
    @GET("products")
    suspend fun getAllProducts(): ProdJsonResponse

    @GET("products")
    suspend fun  getAllProductsStr():String

    @GET("products")
    suspend fun getAllProductsGSON():Response<List<Products>>
}
// Creamos el servicio aqui, esto es estandar.

private var retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

var service: ProdApiService = retrofit.create(ProdApiService::class.java)
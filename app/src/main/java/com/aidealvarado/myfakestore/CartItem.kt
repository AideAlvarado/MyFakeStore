package com.aidealvarado.myfakestore

import java.time.temporal.TemporalAmount

data class CartItem(val id:Int,val title:String, val price:Double,val image:String, var amount: String)
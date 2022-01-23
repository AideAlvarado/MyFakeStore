package com.aidealvarado.myfakestore

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser

object Config{
     val miCartList =  mutableListOf<CartItem>()
     lateinit var user: FirebaseUser
}

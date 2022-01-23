package com.aidealvarado.myfakestore

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "products")
data class Products(
    @PrimaryKey val id: Int?,
    val category: String?,
    val description:String?,
    val image: String?,
    val price: Double?,
    val title: String?,
    var amount: String?
): Serializable

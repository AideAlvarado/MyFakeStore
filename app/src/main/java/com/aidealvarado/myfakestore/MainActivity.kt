package com.aidealvarado.myfakestore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aidealvarado.myfakestore.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

private val TAG = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity() {
    val shoppingList = mutableListOf<CartItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvProduct.layoutManager = LinearLayoutManager(this)
        val adapter = ProductAdapter(this)
        binding.rvProduct.adapter = adapter
        val contexto = this
        val viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(application)
        ).get(MainViewModel::class.java)
        adapter.onItemClickListener = {
            if (it.amount == "0") {
                CoroutineScope(Dispatchers.IO).launch {
                    Log.d(TAG, "Llamando a itemClickListener")
                    val returnValue = viewModel.checkId(it.id!!).await()
                    Log.d(TAG, "Valor retornado $returnValue")
                    withContext(Dispatchers.Main) {
                        it.amount = "1"
                        Toast.makeText(
                            contexto,
                            "Item ${it.title} añadido al carrito",
                            Toast.LENGTH_LONG
                        ).show()
                        viewModel.upsert(it)
                        Config.miCartList.add(CartItem(it.id!!,it.title!!,it.price!!, it.image!! ,it.amount!!))
                        calcularTotal()
                    }
                }
            } else {
                Toast.makeText(
                    contexto,
                    "Item ${it.title} ya está en el carrito",
                    Toast.LENGTH_LONG
                ).show()
            }


        }
        viewModel.eqList.observe(this, { eqList ->
            adapter.submitList(eqList)

        })
        binding.fabCart.setOnClickListener {
            Log.d(TAG, "fabCart On click listener")
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtraJson(shoppingList)
            Log.d(TAG, "Intent ${intent.javaClass.simpleName}")
            startActivity(intent)
        }
    }

    private fun handleEmptyView(eqList: MutableList<Products>, binding: ActivityMainBinding) {
        if (eqList.isEmpty()) {
            Log.d(TAG, "No hay productos")
        } else {
            Log.d(TAG, "Hay productos")

        }
    }
    private fun calcularTotal():BigDecimal{
        var subTotal = 0.toBigDecimal()
        for(item in Config.miCartList){
            val parcial = item.amount.toBigDecimal()* item.price.toBigDecimal()
            subTotal += parcial
        }
        Log.d(TAG,"--------------> subtotal: $subTotal")
        return subTotal
    }
}
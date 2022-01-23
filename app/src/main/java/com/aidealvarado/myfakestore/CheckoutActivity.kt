package com.aidealvarado.myfakestore

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aidealvarado.myfakestore.databinding.ActivityCheckoutBinding
import kotlinx.android.synthetic.main.cart_item.*
import java.math.BigDecimal

private val TAG = CheckoutActivity::class.java.simpleName

class CheckoutActivity : AppCompatActivity() {
    lateinit var binding : ActivityCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myData= intent.getJsonExtra(mutableListOf<CartItem>()::class.java)
        Log.d(TAG, myData.toString())
        for (item in myData!!) {
            Log.d(TAG,"${item} ${item.javaClass.simpleName}")

        }

        binding.rvCart.layoutManager = LinearLayoutManager(this)
        val adapter = CartAdapter(this)
        val contexto = this
        binding.rvCart.adapter = adapter
        Log.d(TAG, "inicializando el viewModel")
        adapter.reCalcularTotal = {calcularTotal()}

        adapter.submitList(Config.miCartList)

        binding.button.setOnClickListener {
            Toast.makeText(
                contexto,
                "Aquí saldria la pantalla de pagos.",
                Toast.LENGTH_LONG
            ).show()
        }


    }

    fun calcularTotal(): BigDecimal {
        var subTotal = 0.toBigDecimal()
        for (item in Config.miCartList) {
            val parcial = item.amount.toBigDecimal() * item.price.toBigDecimal()
            subTotal += parcial
            Log.d(TAG,"nueva cantidad ${item.amount} de ${item.title}")
        }
        Log.d(TAG, "--------------> subtotal: $subTotal")
        binding.editTextNumber.text = subTotal.toString()
        return subTotal
    }
}
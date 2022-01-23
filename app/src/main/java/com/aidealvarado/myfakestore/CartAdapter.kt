package com.aidealvarado.myfakestore

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aidealvarado.myfakestore.databinding.CartItemBinding
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cart_item.*

private val TAG= CartAdapter::class.java.simpleName
class CartAdapter(private val context: Context):ListAdapter<CartItem,CartAdapter.CardViewHolder>(DiffCallBack){
    private var subTotal = 0.0
    companion object DiffCallBack: DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
            return oldItem.id == newItem.id
        }

    }
    lateinit var reCalcularTotal: ()->Unit
    inner class CardViewHolder(private val binding:CartItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(product:CartItem){
            binding.tvTitle.text = product.title
            binding.tvCurrentAmount.text = product.amount
            Glide.with(context).load(product.image).into(binding.ivArtImage)

            binding.btAdd.setOnClickListener {
                Log.d(TAG, "incializando onAddClickListener")
                var amount = binding.tvCurrentAmount.text.toString().toInt()
                amount++
                product.amount = amount.toString()
                binding.tvCurrentAmount.text = amount.toString()
                reCalcularTotal()
            } // onAdd

            binding.btRemove.setOnClickListener{
                Log.d(TAG, "incializando onRemoveClickListener")
                var amount = binding.tvCurrentAmount.text.toString().toInt()
                if (amount > 0) {
                    amount--
                    product.amount = amount.toString()
                    Log.d(TAG,"nueva cantidad ${product.amount} de ${product.title}")
                    binding.tvCurrentAmount.text = amount.toString()
                    reCalcularTotal()

                }
            } // onRemove
            binding.btDelete.setOnClickListener {
                Log.d(TAG, "incializando onDeleteClickListener")
                product.amount = 0.toString()
                binding.tvCurrentAmount .text = 0.toString()
                reCalcularTotal()
            } // onAdd

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context))
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }
}
package com.aidealvarado.myfakestore

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.ListAdapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aidealvarado.myfakestore.databinding.ProductItemBinding
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.product_item.view.*

private val TAG=ProductAdapter::class.java.simpleName
class ProductAdapter(private val context: Context):ListAdapter<Products, ProductAdapter.ProdViewHolder>(DiffCallBack) {
    companion object DiffCallBack:DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id == newItem.id
        }

    }
    lateinit var onItemClickListener: (Products) -> Unit
    inner class ProdViewHolder (private val binding: ProductItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(product:Products){
            binding.categorytv.text = product.category
            binding.pricetv.text= product.price.toString()
            binding.titletv.text = product.title
            Glide.with(context).load(product.image).into(binding.ivArticleImage)
            binding.addtocartbt.setOnClickListener {
                if(::onItemClickListener.isInitialized) {
                    Log.d(TAG,"Se ha inicializado el onClickListener")
                    onItemClickListener(product)
                } else {
                    Log.e(TAG,"No se ha inicializado el onClickListener")
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context))
        return ProdViewHolder(binding)
    }

    override fun onBindViewHolder(holderProd: ProdViewHolder, position: Int) {
        val product = getItem(position)
        holderProd.bind(product)

    }
}



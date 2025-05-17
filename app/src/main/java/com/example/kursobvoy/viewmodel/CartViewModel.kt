package com.example.kursobvoy.Screens

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel

class CartViewModel : ViewModel() {
    private val _cart = mutableStateMapOf<Product, Int>() // Key: Product, Value: count
    val cart: Map<Product, Int> get() = _cart

    fun addToCart(product: Product, count: Int) {
        val newCount = _cart.getOrDefault(product, 0) + count
        if (newCount > 0) {
            _cart[product] = newCount
        } else {
            _cart.remove(product)
        }
    }

    fun getItemCount(product: Product): Int {
        return _cart[product] ?: 0
    }
}

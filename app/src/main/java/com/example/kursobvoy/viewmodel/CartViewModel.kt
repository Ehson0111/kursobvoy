package com.example.kursobvoy.Screens
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel


class CartViewModel : ViewModel() {
    private val _cart = mutableStateMapOf<Product, Int>()
    val cart: Map<Product, Int> get() = _cart

    fun addToCart(product: Product, count: Int) {
        _cart[product] = _cart.getOrDefault(product, 0) + count
    }

    fun getItemCount(product: Product): Int {
        return _cart[product] ?: 0
    }
}
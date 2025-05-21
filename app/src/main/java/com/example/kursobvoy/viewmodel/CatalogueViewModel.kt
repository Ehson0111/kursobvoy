package com.example.kursobvoy.Screens

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kursobvoy.network.ClassificationApi
import kotlinx.coroutines.launch

//class   CatalogueViewModel : ViewModel() {
//    private var _selectedFilters by mutableStateOf<Set<Int>>(emptySet())
//    val selectedFilters: Set<Int>
//        get() = _selectedFilters
//
//    fun updateFilters(newFilters: Set<Int>) {
//        _selectedFilters = newFilters
//    }
//}


class CatalogueViewModel : ViewModel() {
    private var _selectedFilters by mutableStateOf<Set<Int>>(emptySet())
    val selectedFilters: Set<Int>
        get() = _selectedFilters

    private val classificationApi = ClassificationApi()

    fun updateFilters(newFilters: Set<Int>) {
        _selectedFilters = newFilters
    }

    fun testServerConnection(description: String = "паста с сыром") {
        viewModelScope.launch {
            try {
                val category = classificationApi.classifyDescription(description)
                Log.d("CatalogueViewModel", "Получена категория: $category")
            } catch (e: Exception) {
                Log.e("CatalogueViewModel", "Ошибка подключения: ${e.message}")
            }
        }
    }
}
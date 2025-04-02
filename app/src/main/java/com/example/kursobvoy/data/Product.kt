package com.example.kursobvoy.Screens

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Product(
    val id: Int = 0,
    val category_id: Int = 0,
    val name: String = "",
    val description: String = "",
    val image: String = "",
    val price_current: Int = 0,
    val price_old: Int? = null,
    val measure: Int = 0,
    val measure_unit: String = "",
    val energy_per_100_grams: Double = 0.0,
    val proteins_per_100_grams: Double = 0.0,
    val fats_per_100_grams: Double = 0.0,
    val carbohydrates_per_100_grams: Double = 0.0,
    val tag_ids: List<Int> = emptyList()
) {
    // Конструктор без аргументов
    constructor() : this(0, 0, "", "", "", 0, null, 0, "", 0.0, 0.0, 0.0, 0.0, emptyList())
}
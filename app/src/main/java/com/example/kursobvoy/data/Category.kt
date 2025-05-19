package com.example.kursobvoy.Screens

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Category(
    val id: Int = 0,
    val name: String = ""
) {
    constructor() : this(0, "")
}




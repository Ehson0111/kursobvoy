package com.example.kursobvoy.Screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Slider(
    items: List<Category>,
    selectedCategory: Category,
    onItemSelected: (Category) -> Unit
) {
    AppTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            items.forEach { category ->
                CategoryCard(
                    category = category,
                    selected = selectedCategory == category,
                    onCategorySelected = { selectedCategory ->
                        onItemSelected(selectedCategory)
                    }
                )
            }
        }

    }
}
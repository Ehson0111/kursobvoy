package com.example.kursobvoy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kursobvoy.Screens.CatalogueScreen
import com.example.kursobvoy.Screens.Category
import com.example.kursobvoy.Screens.Product
import com.example.kursobvoy.Screens.SignIn
import com.example.kursobvoy.Screens.SignUp
import com.example.kursobvoy.Screens.SplashScreen
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val categories = remember { mutableStateOf(listOf<Category>()) }
            val products = remember { mutableStateOf(listOf<Product>()) }
            val error = remember { mutableStateOf<String?>(null) }
            val isLoading = remember { mutableStateOf(true) } // Состояние загрузки

            // Загружаем данные из Firebase
            loadDataFromFirebase(categories, products, error, isLoading)

            App(categories.value, products.value, error.value, isLoading.value)
        }
    }

    private fun loadDataFromFirebase(
        categoriesState: androidx.compose.runtime.MutableState<List<Category>>,
        productsState: androidx.compose.runtime.MutableState<List<Product>>,
        errorState: androidx.compose.runtime.MutableState<String?>,
        isLoadingState: androidx.compose.runtime.MutableState<Boolean>
    ) {
        try {
            val database = Firebase.database.reference
            Log.d("MainActivity", "Firebase database reference initialized")

            // Загрузка категорий
            database.child("categories")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.d("MainActivity", "Categories snapshot: $snapshot")
                        val categories =
                            snapshot.children.mapNotNull { it.getValue(Category::class.java) }
                        Log.d("MainActivity", "Loaded categories: $categories")
                        categoriesState.value = categories

                        // Проверяем, загружены ли обе ветки
                        if (productsState.value.isNotEmpty()) {
                            isLoadingState.value = false
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.e("MainActivity", "Error loading categories: ${error.message}")
                        errorState.value = "Ошибка загрузки категорий: ${error.message}"
                        isLoadingState.value = false
                    }
                })

            // Загрузка продуктов
            database.child("products").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("MainActivity", "Products snapshot: $snapshot")
                    val products = snapshot.children.mapNotNull { it.getValue(Product::class.java) }
                    Log.d("MainActivity", "Loaded products: $products")
                    productsState.value = products

                    // Проверяем, загружены ли обе ветки
                    if (categoriesState.value.isNotEmpty()) {
                        isLoadingState.value = false
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("MainActivity", "Error loading products: ${error.message}")
                    errorState.value = "Ошибка загрузки продуктов: ${error.message}"
                    isLoadingState.value = false
                }
            })
        } catch (e: Exception) {
            Log.e("MainActivity", "Exception in loadDataFromFirebase: ${e.message}", e)
            errorState.value = "Исключение при загрузке данных: ${e.message}"
            isLoadingState.value = false
        }
    }

    @Composable
    private fun App(
        categories: List<Category>,
        products: List<Product>,
        error: String?,
        isLoading: Boolean
    ) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "splash") {
            composable("SignUp") {
                SignUp(
                    navController = navController,
                )
            }
            composable("SignIn") {
                SignIn(
                    navController = navController,
                )
            }
            composable("splash") {
                // Показываем SplashScreen, пока данные загружаются
                SplashScreen(
                    navController = navController,
                    isLoading = isLoading,
                    error = error,
                    onDataLoaded = {
                        // Переходим на каталог, когда данные загружены
                        navController.navigate("catalogue") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                )
            }
            composable("catalogue") {
                CatalogueScreen(
                    navController = navController,
                    categories = categories,
                    products = products,
                    cartViewModel = viewModel(),
                    catalogueViewModel = viewModel()
                )
            }
//            composable("item/{productId}") { backStackEntry ->
//                val productId = backStackEntry.arguments?.getString("productId")
//                val product = products.firstOrNull { it.id.toString() == productId }
//                if (product != null) {
//                    ItemScreen(
//                        product = product,
//                        cartViewModel = viewModel(),
//                        navController = navController
//                    )
//                } else {
//                    Box(
//                        modifier = Modifier.fillMaxSize(),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(text = "Продукт с ID $productId не найден")
//                    }
//                }
//            }
//            composable("cart") {
//                CartScreen(
//                    navController = navController,
//                    cartViewModel = viewModel()
//                )
//            }
//            composable("celebrate") {
//                CelebrateScreen(navController)
//            }
        }
    }
}
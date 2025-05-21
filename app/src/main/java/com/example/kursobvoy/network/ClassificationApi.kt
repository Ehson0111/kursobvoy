//package com.example.kursobvoy.network
//
//import io.ktor.client.call.*
//import io.ktor.client.request.*
//import io.ktor.http.*
//import kotlinx.serialization.Serializable
//import android.util.Log
//
//@Serializable
//data class ClassificationRequest(val description: String)
//
//@Serializable
//data class ClassificationResponse(val category: String)
//
//class ClassificationApi {
//    private val baseUrl = "http://172.20.10.3:5000"
//
//    suspend fun classifyDescription(description: String): String? {
//        return try {
//            Log.d("ClassificationApi", "Отправляем запрос: $description")
//            val response = ApiClient.client.post("$baseUrl/classify") {
//                contentType(ContentType.Application.Json)
//                setBody(ClassificationRequest(description))
//            }
//            Log.d("ClassificationApi", "HTTP статус: ${response.status}")
//            if (response.status != HttpStatusCode.OK) {
//                val errorBody = response.body<String>()
//                Log.e("ClassificationApi", "Ошибка сервера: $errorBody")
//                return null
//            }
//            val responseBody: ClassificationResponse = response.body()
//            Log.d("ClassificationApi", "Десериализованный ответ: $responseBody")
//            responseBody.category
//        } catch (e: Exception) {
//            Log.e("ClassificationApi", "Ошибка: ${e.message}", e)
//            null
//        }
//    }
//}


package com.example.kursobvoy.network

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import android.util.Log

@Serializable
data class ClassificationRequest(val description: String)

@Serializable
data class ClassificationResponse(val category: String)

class ClassificationApi {
    private val baseUrl = "http://172.20.10.3:5000"

    suspend fun classifyDescription(description: String): String? {
        return try {
            Log.d("ClassificationApi", "Отправляем запрос: $description")
            val response = ApiClient.client.post("$baseUrl/classify") {
                contentType(ContentType.Application.Json)
                setBody(ClassificationRequest(description))
            }
            Log.d("ClassificationApi", "HTTP статус: ${response.status}")
            if (response.status != HttpStatusCode.OK) {
                val errorBody = response.body<String>()
                Log.e("ClassificationApi", "Ошибка сервера: $errorBody")
                return null
            }
            val responseBody: ClassificationResponse = response.body()
            Log.d("ClassificationApi", "Десериализованный ответ: $responseBody")
            responseBody.category
        } catch (e: Exception) {
            Log.e("ClassificationApi", "Ошибка: ${e.message}", e)
            null
        }
    }
}
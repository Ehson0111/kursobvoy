package com.example.kursobvoy.Screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kursobvoy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.rpc.context.AttributeContext.Auth


@Composable

fun SignUp(navController: NavController) {
    var auth = Firebase.auth
    var fullname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var createpassword by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .padding(start = 50.dp, end = 50.dp)
            .fillMaxSize(),
//        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center


    ) {
        Image(
            contentDescription = "sjdf",
            painter = painterResource(R.drawable.signup),
            modifier = Modifier
                .size(300.dp)
                .padding(bottom = 45.dp)
        )

        Text("GET STARTED WITH FOODIES", fontSize = 16.sp)
        OutlinedTextField(
            value = fullname,
            onValueChange = { fullname = it },
            label = { Text("Full name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            shape = RoundedCornerShape(10.dp)

        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            shape = RoundedCornerShape(10.dp)

        )

        OutlinedTextField(
            value = createpassword,
            onValueChange = { createpassword = it },
            label = { Text("Create Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            shape = RoundedCornerShape(10.dp)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = {
                signup(auth,email.trim(),createpassword.trim())
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.button),
                contentColor = colorResource(R.color.white)
            )
        ) {
            Text("Sign up")
        }
    }

}

private fun signup(auth: FirebaseAuth, email: String, password: String) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("SignUp", "Успешная регистрация!")
                // navController.navigate("home") // Перенаправление
            } else {
                val error = when {
                    task.exception is FirebaseAuthInvalidCredentialsException ->
                        "Некорректный email или пароль (минимум 6 символов)"
                    task.exception is FirebaseAuthUserCollisionException ->
                        "Этот email уже зарегистрирован"
                    task.exception?.message?.contains("API key") == true ->
                        "Ошибка Firebase. Проверьте google-services.json"
                    else -> "Ошибка: ${task.exception?.message}"
                }
                Log.e("SignUp", error, task.exception)
            }
        }
}
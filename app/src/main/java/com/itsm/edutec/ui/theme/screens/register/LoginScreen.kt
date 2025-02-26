package com.itsm.edutec.ui.theme.screens.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.itsm.edutec.R

@Composable
fun LoginScreen(navHostController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_animation))

    val progress by animateLottieCompositionAsState(
        isPlaying = true,
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 0.5f
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.CenterHorizontally),
            composition = composition,
            progress = { progress }
        )

        Text(
            text = "EduTec",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    emailError.ifEmpty { "Email" },
                    color = if (emailError.isNotEmpty()) Color.Red else Color.Black
                )
            },
            leadingIcon = {
                Icon(Icons.Rounded.AccountCircle, contentDescription = null)
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 20.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(
                    passwordError.ifEmpty { "Contraseña" },
                    color = if (passwordError.isNotEmpty()) Color.Red else Color.Black
                )
            },
            leadingIcon = {
                Icon(Icons.Rounded.Lock, contentDescription = null)
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 20.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                emailError = if (email.isBlank()) "Email inválido" else ""
                passwordError = if (password.isBlank()) "Contraseña inválida" else ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 90.dp)
        ) {
            Text(text = "Ingresar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿Olvidaste la contraseña?",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                navHostController.navigate("forgot_password")
            }
        )

        Spacer(modifier = Modifier.height(50.dp))

        Row {
            Text(text = "¿No eres miembro? ")

            Text(
                text = "Regístrate ahora",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    navHostController.navigate("register_user")
                }
            )
        }
    }
}
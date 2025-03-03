package com.itsm.edutec.ui.theme.navigation

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun NavigateToLoginButton(navController: NavController) {
    TextButton(
        onClick = {
            navController.navigate("login") {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    ) {
        Text(text = "Iniciar sesión")
    }
}
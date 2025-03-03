package com.itsm.edutec.ui.theme.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController


@Composable
fun BackToLoginButton(navController: NavController) {
    val isDarkTheme = isSystemInDarkTheme()

    IconButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier
            .padding(16.dp)
            .size(60.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = if (!isDarkTheme) Color.DarkGray else Color.Transparent,
            contentColor = if (isDarkTheme) Color.White else Color(0xFFFFEAA0)
        )
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBackIosNew,
            contentDescription = "Volver",
            modifier = Modifier
                .padding(16.dp)
                .zIndex(1f)
                .size(48.dp)
        )
    }
}

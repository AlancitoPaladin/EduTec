package com.itsm.edutec.ui.theme.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itsm.edutec.R
import com.itsm.edutec.ui.theme.components.GradientButton
import com.itsm.edutec.ui.theme.navigation.BackToLoginButton


@Composable
fun ForgotPassword(navController: NavController) {
    val offset = Offset(3.0f, 4.0f)
    val
            gradientColors = listOf(Color(0xFF36D8F5), Color(0xFF7EF8CF))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.Transparent
            )
    ) {
        Box(
            modifier = Modifier.align(Alignment.TopStart),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    FadeImageFromCenterToBottom(navController)
                }

                Spacer(modifier = Modifier.height(140.dp))

                Text(
                    "Recuperar contraseña",
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize(),
                    style = TextStyle(
                        fontSize = 32.sp,
                        brush = Brush.linearGradient(
                            colors = gradientColors
                        ),
                        shadow = Shadow(
                            color = MaterialTheme.colorScheme.primary,
                            offset = offset,
                            blurRadius = 0.01f
                        ),
                        fontWeight = FontWeight.W300
                    )
                )

                Spacer(modifier = Modifier.height(40.dp))

                ResetEmailID()

                Spacer(modifier = Modifier.padding(3.dp))

                val gradientColor = listOf(Color(0xFF58C8E7), Color(0xFF56D2DC))
                val cornerRadius = 32.dp

                Spacer(modifier = Modifier.padding(10.dp))

                GradientButton(
                    gradientColors = gradientColor,
                    cornerRadius = cornerRadius,
                    nameButton = "Enviar",
                    roundedCornerShape = RoundedCornerShape(topStart = 25.dp, bottomEnd = 25.dp)
                )
            }
        }
    }
}

@Composable
fun ResetEmailID() {
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        shape = RoundedCornerShape(topEnd = 12.dp, bottomStart = 12.dp),
        label = {
            Text(
                text = "Correo electrónico",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium
            )
        },
        placeholder = { Text(text = "Correo electrónico") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                // Más lógica es possible
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}

@Composable
fun FadeImageFromCenterToBottom(navController: NavController) {
    val image = painterResource(id = R.drawable.forget_password)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Image(
            painter = image,
            contentDescription = "Password",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    alpha = 0.99f
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent {
                    drawContent()
                    val fadeBrush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black,
                            Color.Transparent
                        ),
                        startY = size.height / 30,
                        endY = size.height
                    )
                    drawRect(brush = fadeBrush, blendMode = BlendMode.DstIn)
                }
        )
        BackToLoginButton(navController)
    }
}
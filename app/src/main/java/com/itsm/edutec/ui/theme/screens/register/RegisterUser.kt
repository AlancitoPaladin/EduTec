package com.itsm.edutec.ui.theme.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.itsm.edutec.R
import com.itsm.edutec.ui.theme.components.GradientButton
import com.itsm.edutec.ui.theme.navigation.BackToLoginButton


@Composable
fun RegisterUser(navController: NavController) {
    val attributes = listOf("Nombre", "Apellidos", "Correo electrónico")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopStart
        ) {
            BackToLoginButton(navController)
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp)
                )
        ) {

            Image(
                painter = painterResource(id = R.drawable.user_reg),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(150.dp))

                Text(
                    text = "Crear una cuenta",
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .padding(40.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                attributes.forEach { fieldName ->
                    Registers(fieldName)
                    Spacer(modifier = Modifier.height(3.dp))
                }

                RegisterPassword()
                Spacer(modifier = Modifier.height(3.dp))
                RegisterPasswordConfirm()
                Spacer(modifier = Modifier.height(3.dp))
                RoleMenu()

                val gradientColor = listOf(Color(0xFF00CCFF), Color(0xFF57CBFD))
                val cornerRadius = 32.dp

                Spacer(modifier = Modifier.height(10.dp))

                GradientButton(
                    gradientColors = gradientColor,
                    cornerRadius = cornerRadius,
                    nameButton = "Crear cuenta",
                    roundedCornerShape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp)
                )
            }
        }
    }
}


@Composable
fun Registers(name: String) {
    var text: String by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        shape = RoundedCornerShape(topEnd = 12.dp, bottomStart = 12.dp),
        label = {
            Text(
                text = name,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
            )
        },
        placeholder = { Text(text = name) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
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
fun RegisterPasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    passwordHidden: Boolean,
    onPasswordVisibilityToggle: () -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(topEnd = 12.dp, bottomStart = 12.dp),
        label = {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
            )
        },
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                // Lógica adicional aquí si es necesaria
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
        ),
        trailingIcon = {
            IconButton(onClick = onPasswordVisibilityToggle) {
                Icon(
                    imageVector = if (passwordHidden) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (passwordHidden) "Mostrar contraseña" else "Ocultar contraseña"
                )
            }
        },
        modifier = Modifier.fillMaxWidth(0.8f),
        singleLine = true
    )
}

@Composable
fun RegisterPassword() {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    RegisterPasswordField(
        label = "Ingresa la contraseña",
        value = password,
        onValueChange = { password = it },
        passwordHidden = passwordHidden,
        onPasswordVisibilityToggle = { passwordHidden = !passwordHidden }
    )
}

@Composable
fun RegisterPasswordConfirm() {
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var confirmPasswordHidden by rememberSaveable { mutableStateOf(true) }

    RegisterPasswordField(
        label = "Confirma la contraseña",
        value = confirmPassword,
        onValueChange = { confirmPassword = it },
        passwordHidden = confirmPasswordHidden,
        onPasswordVisibilityToggle = { confirmPasswordHidden = !confirmPasswordHidden }
    )
}

@Composable
fun RoleMenu() {
    var value by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .clickable { expanded = true }
    ) {
        TextField(
            value = value,
            onValueChange = { },
            label = {
                Text(
                    "Selecciona tu rol",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium
                )
            },
            shape = RoundedCornerShape(topEnd = 12.dp, bottomStart = 12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Menu",
                    modifier = Modifier.size(24.dp)
                )
            },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text("Alumno") },
            onClick = {
                value = "Alumno"
                expanded = false
            }
        )
        DropdownMenuItem(
            text = { Text("Maestro") },
            onClick = {
                value = "Maestro"
                expanded = false
            }
        )
    }
}

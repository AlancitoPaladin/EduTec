package com.itsm.edutec.ui.theme.screens.register

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.itsm.edutec.R
import com.itsm.edutec.ui.theme.api.ApiService
import com.itsm.edutec.ui.theme.components.GradientButton
import com.itsm.edutec.ui.theme.navigation.BackToLoginButton


@Composable
fun RegisterUser(navController: NavController, apiService: ApiService) {
    val factory = remember { RegisterViewModelFactory(apiService) }
    val viewModel: RegisterViewModel = viewModel(factory = factory)

    var name by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var role by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    var isPasswordHidden by rememberSaveable { mutableStateOf(true) }
    var isConfirmPasswordHidden by rememberSaveable { mutableStateOf(true) }

    val uiState = viewModel.uiState

    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Success -> {
                Toast.makeText(context, uiState.message, Toast.LENGTH_LONG).show()
                navController.navigate("login")
            }

            is UiState.Error -> {
                Toast.makeText(context, uiState.message, Toast.LENGTH_LONG).show()
            }

            else -> Unit
        }
    }


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

                Registers("Nombre", name) { name = it }
                Spacer(modifier = Modifier.height(3.dp))

                Registers("Apellidos", lastName) { lastName = it }
                Spacer(modifier = Modifier.height(3.dp))

                Registers("Correo electrónico", email) { email = it }
                Spacer(modifier = Modifier.height(3.dp))

                RegisterPasswordField(
                    "Ingresa la contraseña",
                    password,
                    { password = it },
                    passwordHidden = isPasswordHidden,
                    onPasswordVisibilityToggle = {
                        isPasswordHidden = !isPasswordHidden
                    }
                )

                RegisterPasswordField(
                    "Confirma la contraseña",
                    confirmPassword,
                    { confirmPassword = it },
                    passwordHidden = isConfirmPasswordHidden,
                    onPasswordVisibilityToggle = {
                        isConfirmPasswordHidden = !isConfirmPasswordHidden
                    }
                )

                Spacer(modifier = Modifier.height(3.dp))

                RoleMenu(role = role) { role = it }

                Spacer(modifier = Modifier.height(10.dp))

                val gradientColor = listOf(Color(0xFF00CCFF), Color(0xFF57CBFD))
                val cornerRadius = 32.dp

                Spacer(modifier = Modifier.height(10.dp))

                val isFormValid = name.isNotBlank() &&
                        lastName.isNotBlank() &&
                        email.isNotBlank() &&
                        password == confirmPassword &&
                        password.length >= 6 &&
                        role.isNotBlank()


                GradientButton(
                    gradientColors = gradientColor,
                    cornerRadius = cornerRadius,
                    nameButton = "Crear cuenta",
                    roundedCornerShape = RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp),
                    onClick = {
                        if (isFormValid) {
                            val registerRequest = RegisterRequest(
                                name = name,
                                lastName = lastName,
                                email = email,
                                password = password,
                                role = role
                            )
                            viewModel.registerUser(registerRequest)
                        } else {
                            Toast.makeText(
                                context,
                                "Por favor, completa todos los campos correctamente.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                )

            }
        }
    }
}


@Composable
fun Registers(name: String, value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
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
    val keyboardController = LocalSoftwareKeyboardController.current

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
                keyboardController?.hide()
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
fun RoleMenu(role: String, onRoleSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .clickable { expanded = true }
    ) {
        TextField(
            value = role,
            onValueChange = {},
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
                onRoleSelected("Alumno")
                expanded = false
            }
        )
        DropdownMenuItem(
            text = { Text("Maestro") },
            onClick = {
                onRoleSelected("Maestro")
                expanded = false
            }
        )
    }
}

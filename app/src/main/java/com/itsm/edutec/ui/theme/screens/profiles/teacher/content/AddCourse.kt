package com.itsm.edutec.ui.theme.screens.profiles.teacher.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.itsm.edutec.ui.theme.models.TeacherViewModel
import com.itsm.edutec.ui.theme.models.TeacherViewModelFactory
import com.itsm.edutec.ui.theme.session.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCourse(navController: NavController) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val factory = remember { TeacherViewModelFactory(sessionManager) }
    val viewModel: TeacherViewModel = viewModel(factory = factory)

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Crear curso")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier.padding(innerPadding)
        ) {
            AddContent(viewModel = viewModel) {
                navController.popBackStack()
            }
        }

    }
}

@Composable
fun AddContent(viewModel: TeacherViewModel, onCourseCreated: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("") }
    var start by remember { mutableStateOf("") }
    var end by remember { mutableStateOf("") }
    var monthError by remember { mutableStateOf<String?>(null) }

    val errorMessage = viewModel.createCourseError

    val monthIndex = mapOf(
        "Enero" to 1, "Febrero" to 2, "Marzo" to 3, "Abril" to 4,
        "Mayo" to 5, "Junio" to 6, "Julio" to 7, "Agosto" to 8,
        "Septiembre" to 9, "Octubre" to 10, "Noviembre" to 11, "Diciembre" to 12
    )

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text("Crear nuevo curso", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título del curso") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = imageUrl,
            onValueChange = { imageUrl = it },
            label = { Text("URL de la imagen") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Categoría") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = level,
            onValueChange = { level = it },
            label = { Text("Nivel") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        MonthDropdown("Mes de inicio", start) { start = it }
        Spacer(modifier = Modifier.height(8.dp))
        MonthDropdown("Mes de fin", end) { end = it }

        if (!monthError.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = monthError!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (!errorMessage.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val startMonth = monthIndex[start] ?: 0
                val endMonth = monthIndex[end] ?: 0

                if (startMonth == 0 || endMonth == 0) {
                    monthError = "Selecciona ambos meses correctamente"
                    return@Button
                }

                if (startMonth >= endMonth) {
                    monthError = "El mes de inicio debe ser anterior al de fin"
                    return@Button
                }

                monthError = null

                if (title.isNotBlank() && description.isNotBlank()) {
                    viewModel.createCourse(
                        title = title,
                        description = description,
                        imageUrl = imageUrl,
                        category = category,
                        level = level,
                        start = start,
                        end = end
                    ) {
                        title = ""
                        description = ""
                        imageUrl = ""
                        category = ""
                        level = ""
                        start = ""
                        end = ""
                        onCourseCreated()
                    }
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Crear Curso")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthDropdown(label: String, selectedMonth: String, onMonthSelected: (String) -> Unit) {
    val months = listOf(
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    )
    var expanded by remember { mutableStateOf(false) }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedMonth,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier =
                Modifier
                    .menuAnchor(type = MenuAnchorType.PrimaryEditable, enabled = true)
                    .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            months.forEach { month ->
                DropdownMenuItem(
                    text = { Text(month) },
                    onClick = {
                        onMonthSelected(month)
                        expanded = false
                    }
                )
            }
        }
    }
}

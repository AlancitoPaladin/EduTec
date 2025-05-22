package com.itsm.edutec.ui.theme.screens.profiles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itsm.edutec.ui.theme.api.Course
import com.itsm.edutec.ui.theme.api.CourseApiClient
import com.itsm.edutec.ui.theme.components.MyGlideImageWithView
import com.itsm.edutec.ui.theme.models.CourseContentState
import com.itsm.edutec.ui.theme.models.CourseContentViewModel
import com.itsm.edutec.ui.theme.models.CourseContentViewModelFactory
import com.itsm.edutec.ui.theme.screens.profiles.teacher.content.Announcement
import com.itsm.edutec.ui.theme.screens.profiles.teacher.content.Assignment
import com.itsm.edutec.ui.theme.screens.profiles.teacher.content.Material

@Composable
fun CourseView(course: Course?, isEditable: Boolean) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFF175557)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .size(width = 390.dp, height = 220.dp)
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyGlideImageWithView(
                imageUrl = course?.image ?: "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = course?.course ?: "No hay curso seleccionado",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = course?.description ?: "",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    CourseContentScreen(courseId = course?.id ?: "", isEditable)
}

@Composable
fun CourseContentScreen(
    courseId: String,
    isEditable: Boolean,
    viewModel: CourseContentViewModel = viewModel(
        factory = CourseContentViewModelFactory(CourseApiClient.courseService)
    )
) {
    val state = viewModel.courseContentState.collectAsState().value

    LaunchedEffect(courseId) {
        viewModel.loadCourseContent(courseId)
    }

    when (state) {
        is CourseContentState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }

        is CourseContentState.Success -> {
            val content = state.content
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    Text("Anuncios", style = MaterialTheme.typography.titleLarge)
                }
                items(content.announcements) { announcement ->
                    AnnouncementItem(announcement = announcement, isEditable = isEditable)
                }

                item {
                    Spacer(Modifier.height(16.dp))
                    Text("Tareas", style = MaterialTheme.typography.titleLarge)
                }
                items(content.assignments) { assignment ->
                    AssignmentItem(assignment = assignment, isEditable = isEditable)
                }

                item {
                    Spacer(Modifier.height(16.dp))
                    Text("Materiales", style = MaterialTheme.typography.titleLarge)
                }
                items(content.materials) { material ->
                    MaterialItem(material = material, isEditable = isEditable)
                }
            }
        }

        is CourseContentState.Error -> {
            Text(
                text = state.message,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun AnnouncementItem(announcement: Announcement, isEditable: Boolean) {
    var showDialog by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("- ${announcement.title}: ${announcement.content}", modifier = Modifier.weight(1f))
        if (isEditable) {
            IconButton(onClick = {
                showDialog = true
            }) {
                Icon(Icons.Default.Edit, contentDescription = "Editar nuncio")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            },
            title = {
                Text("Aviso")
            },
            text = {
                Text("Función en desarrollo.")
            }
        )
    }
}

@Composable
fun AssignmentItem(assignment: Assignment, isEditable: Boolean) {
    var showDialog by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("- ${assignment.title}: ${assignment.description}", modifier = Modifier.weight(1f))
        if (isEditable) {
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Edit, contentDescription = "Editar tarea")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            },
            title = {
                Text("Aviso")
            },
            text = {
                Text("Función en desarrollo.")
            }
        )
    }
}

@Composable
fun MaterialItem(material: Material, isEditable: Boolean) {
    var showDialog by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("- ${material.title}: ${material.content}", modifier = Modifier.weight(1f))
        if (isEditable) {
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar material")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            },
            title = {
                Text("Aviso")
            },
            text = {
                Text("Función en desarrollo.")
            }
        )
    }
}

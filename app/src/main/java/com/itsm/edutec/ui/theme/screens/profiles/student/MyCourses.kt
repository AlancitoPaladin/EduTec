package com.itsm.edutec.ui.theme.screens.profiles.student

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.itsm.edutec.ui.theme.api.CoursePreview
import com.itsm.edutec.ui.theme.components.MyGlideImageWithView
import com.itsm.edutec.ui.theme.session.SessionManager
import kotlinx.coroutines.launch

@Composable
fun MyCourses(
    viewModel: MyCoursesViewModel = viewModel(),
    navController: NavController,
    padding: PaddingValues
) {
    val courses by viewModel.myCourses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val sessionManager = SessionManager(context)
        val userEmail = sessionManager.getUserEmail()

        userEmail?.let {
            viewModel.fetchStudentCourses(it)
        }
    }

    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $error", color = Color.Red)
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                items(courses) { course ->
                    VerticalCourseCard(course = course) {
                        navController.navigate("courseView/${course.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun VerticalCourseCard(
    course: CoursePreview,
    onClick: (CoursePreview) -> Unit,
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    ElevatedCard(
        onClick = { onClick(course) },
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .width(360.dp)
            .height(140.dp)
            .padding(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(75.dp)
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MyGlideImageWithView(
                        imageUrl = course.image,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = course.course,
                        color = Color.White,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = course.description,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "${course.stars} ★",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }

            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.White
                )
            }

            IconButtonWithDialog(
                id = course.id,
                showDialog = showDialog,
                onDialogChange = { showDialog = it },
                onConfirm = {
                    Toast.makeText(context, "Curso eliminado", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}


@Composable
fun IconButtonWithDialog(
    id: String,
    showDialog: Boolean,
    onDialogChange: (Boolean) -> Unit,
    onConfirm: () -> Unit,
    viewModel: DeleteViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDialogChange(false) },
            title = { Text("¿Estás seguro?") },
            text = { Text("Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    onDialogChange(false)

                    scope.launch {
                        val sessionManager = SessionManager(context)
                        val userEmail = sessionManager.getUserEmail()

                        if (userEmail != null) {
                            viewModel.deleteCourse(id, userEmail)
                        }

                        onConfirm()
                    }
                }) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDialogChange(false) }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

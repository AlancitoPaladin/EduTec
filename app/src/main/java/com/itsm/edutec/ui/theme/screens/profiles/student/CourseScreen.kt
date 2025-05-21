package com.itsm.edutec.ui.theme.screens.profiles.student

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.itsm.edutec.ui.theme.api.Course
import com.itsm.edutec.ui.theme.components.MyGlideImageWithView
import com.itsm.edutec.ui.theme.models.CourseDetails
import com.itsm.edutec.ui.theme.session.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseScreen(
    id: String,
    navController: NavController,
    viewModel: CourseDetails = viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val course by viewModel.course.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState()
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

    val userEmail by produceState<String?>(initialValue = null) {
        value = sessionManager.getUserEmail()
    }

    LaunchedEffect(id) {
        viewModel.fetchCourseDetails(id)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Curso",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                errorMessage != null -> {
                    Text(
                        text = "Error: $errorMessage",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                course != null && userEmail != null -> {
                    // Add scroll here
                    val scrollState = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        CourseContent(course!!, userEmail!!)
                    }
                }

                else -> {
                    Text(
                        text = "No course details available.",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun CourseContent(course: Course, studentEmail: String) {
    Spacer(modifier = Modifier.height(16.dp))

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.TopEnd
        ) {
            Text(
                text = "${course.stars} ⭐",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.TopEnd)
            )

        }

        Text(
            text = course.course,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            MyGlideImageWithView(
                imageUrl = course.image,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(16.dp))
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            HorizontalDivider(thickness = 2.dp)

            Text(
                text = course.description,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text =
                    """
                    Categoria: ${course.category}
                    
                    Nivel: ${course.level}
                    
                    Inicio: ${course.start} - ${course.year} 
                    Fin: ${course.end} - ${course.year}
                    
                """.trimIndent(),
                lineHeight = 18.sp
            )

            HorizontalDivider(thickness = 2.dp)

            Text(
                text = "Contacto",
                maxLines = 1,
                fontSize = 18.sp,
                style = MaterialTheme.typography.labelLarge,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = course.teacherEmail,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )


        }

        HorizontalDivider(thickness = 3.dp)

        EnrollCourse(course.id, studentEmail)
    }
}


@Composable
fun EnrollCourse(
    courseId: String,
    studentEmail: String,
    enrollViewModel: EnrollViewModel = viewModel()
) {
    val isLoading by enrollViewModel.isLoading.collectAsState()
    val enrollmentStatus by enrollViewModel.enrollmentStatus.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(enrollmentStatus) {
        enrollmentStatus?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    ElevatedButton(
        onClick = {
            if (!isLoading) {
                enrollViewModel.enroll(courseId, studentEmail)
            }
        },
        colors = ButtonDefaults.elevatedButtonColors(Color.White),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                color = Color.Black
            )
        } else {
            Text("Inscribirme", color = Color.Black)
        }
    }
}

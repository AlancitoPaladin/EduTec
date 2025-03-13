package com.itsm.edutec.ui.theme.screens.profiles.student

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itsm.edutec.ui.theme.api.CoursePreview
import com.itsm.edutec.ui.theme.components.BottomBar
import com.itsm.edutec.ui.theme.components.MyGlideImageWithView
import com.itsm.edutec.ui.theme.models.CourseViewModel

@Preview
@Composable
fun MyPreview() {
    MaterialTheme {
        StudentScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentScreen(courseViewModel: CourseViewModel = viewModel()) {
    val isDarkTheme = isSystemInDarkTheme()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val courses = courseViewModel.courses.collectAsState().value

    LaunchedEffect(Unit) {
        courseViewModel.fetchCourses()
    }

    val firstSection = courses.take(4)
    val secondSection = courses.drop(4).take(12)
    val thirdSection = courses.takeLast(4)

    Scaffold(
        containerColor = if (isDarkTheme) Color(0xFF262626) else Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        text = "EduTec",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { /* Acción de navegación */ },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = if (isDarkTheme) Color.White else Color.Cyan
                        ),
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* Acción de notificación */ },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = if (isDarkTheme) Color.White else Color.Cyan
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Rounded.Notifications,
                            contentDescription = "Notificaciones"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = { BottomBar() }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { CourseCategory(categoryName = "Novedades") }
            item { CourseCard(courses = firstSection) }

            item { CourseCategory(categoryName = "Cursos populares") }
            item { VariousCoursesCard(courses = secondSection) }

            item { CourseCategory(categoryName = "Recomendados para ti") }
            item { CourseCard(courses = thirdSection) }
        }
    }
}


@Composable
fun CourseCard(courses: List<CoursePreview>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(courses) { index, course ->
            ElevatedCard(
                onClick = { /* Ir al curso */ },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .size(width = 300.dp, height = 210.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    MyGlideImageWithView(
                        imageUrl = course.image,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.85f)
                            .clip(RoundedCornerShape(32.dp))
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 2.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = course.course,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = if (!isSystemInDarkTheme()) Color.Black else Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            "${course.stars}",
                            fontSize = 12.sp,
                            color = if (!isSystemInDarkTheme()) Color.Black else Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .padding(8.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Star",
                            tint = Color.Yellow,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun VariousCoursesCard(courses: List<CoursePreview>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        val rows = courses.chunked(3)
        rows.forEach { rowCourses ->
            Column(modifier = Modifier.padding(bottom = 12.dp)) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    itemsIndexed(rowCourses) { index, course ->
                        ElevatedCard(
                            onClick = { /* Ir al curso */ },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            modifier = Modifier
                                .width(320.dp)
                                .height(110.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(18.dp, 18.dp)
                                    .height(90.dp)
                                    .width(300.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(75.dp)
                                        .height(85.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    MyGlideImageWithView(
                                        imageUrl = course.image,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight()
                                    )
                                }

                                Spacer(modifier = Modifier.width(18.dp))

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Column {
                                        Text(
                                            text = course.course,
                                            color = Color.White,
                                            fontSize = 12.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )

                                        Text(
                                            text = course.description,
                                            maxLines = 1,
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
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CourseCategory(categoryName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = categoryName,
            fontSize = 22.sp,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        IconButton(onClick = { /* Navegar a categoría */ }) {
            Icon(
                imageVector = Icons.Filled.KeyboardDoubleArrowRight,
                contentDescription = "Ver más",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

package com.itsm.edutec.ui.theme.screens.profiles.student

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itsm.edutec.ui.theme.components.BottomBar

@Preview(showBackground = true)
@Composable
fun MyPreview() {
    StudentScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentScreen() {
    val isDarkTheme = isSystemInDarkTheme()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text(
                        text = "EduTec",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { /* do something */ },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = if (isDarkTheme) Color.White else Color.Cyan
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description",
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /* do something */ },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = if (isDarkTheme) Color.White else Color.Cyan
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Notifications,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
        bottomBar = {
            BottomBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            CourseCard()

            CourseCategory()

            CourseCard()

            CourseCategory()

            CourseCard()
        }
    }
}

@Composable
fun CourseCard() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(4) {
                ElevatedCard(
                    onClick = { /* Ir al curso */ },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onSecondaryContainer,

                        ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .size(width = 360.dp, height = 150.dp)
                ) {
                    Text(
                        text = "Elevated",
                        modifier = Modifier
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }
    }
}


@Composable
fun CourseCategory() {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "Category 1",
            fontSize = 24.sp,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Color.DarkGray
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.KeyboardDoubleArrowRight,
                    contentDescription = "Volver"
                )
            }
        }
    }
}
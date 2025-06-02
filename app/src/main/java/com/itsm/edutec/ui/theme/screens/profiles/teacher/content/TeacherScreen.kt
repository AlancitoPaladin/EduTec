package com.itsm.edutec.ui.theme.screens.profiles.teacher.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.itsm.edutec.ui.theme.models.TeacherViewModel
import com.itsm.edutec.ui.theme.models.TeacherViewModelFactory
import com.itsm.edutec.ui.theme.screens.profiles.CourseView
import com.itsm.edutec.ui.theme.session.SessionManager
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherScreen(navController: NavController) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val factory = TeacherViewModelFactory(sessionManager)
    val teacherViewModel = ViewModelProvider(
        LocalContext.current as ViewModelStoreOwner,
        factory
    )[TeacherViewModel::class.java]

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedPrefItem = remember { mutableStateOf<String?>(null) }

    val courses by remember { derivedStateOf { teacherViewModel.courses } }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Mis cursos",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge
                    )

                    HorizontalDivider()

                    Text(
                        "Cursos",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )

                    courses.forEach { course ->
                        DrawerOption(
                            label = course.course,
                            isSelected = teacherViewModel.selectedCourse?.id == course.id,
                            onClick = {
                                teacherViewModel.selectedCourse(course)
                                selectedPrefItem.value = null
                                scope.launch { drawerState.close() }
                            }
                        )
                    }


                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        "Preferencias",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )

                    DrawerOption(
                        label = "Configuración",
                        icon = Icons.Outlined.Settings,
                        isSelected = selectedPrefItem.value == "Configuración",
                        onClick = {
                            selectedPrefItem.value = "Configuración"
                            teacherViewModel.selectedCourse = null
                            scope.launch { drawerState.close() }
                        }
                    )

                    DrawerOption(
                        label = "Ayuda y soporte",
                        icon = Icons.AutoMirrored.Outlined.Help,
                        isSelected = selectedPrefItem.value == "Ayuda y soporte",
                        onClick = {
                            selectedPrefItem.value = "Ayuda y soporte"
                            teacherViewModel.selectedCourse = null
                            scope.launch { drawerState.close() }
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    DrawerOption(
                        label = "Cerrar sesión",
                        icon = Icons.AutoMirrored.Outlined.ExitToApp,
                        isSelected = selectedPrefItem.value == "Cerrar sesión",
                        onClick = {
                            selectedPrefItem.value = "Cerrar sesión"
                            teacherViewModel.selectedCourse = null

                            scope.launch {
                                drawerState.close()
                                sessionManager.clearSession()
                                navController.navigate("login") {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        }
                    )


                    Spacer(Modifier.height(12.dp))
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("EduTec")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open()
                                else drawerState.close()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            floatingActionButton = {
                NewCourse(navController)
            }
        ) { innerPadding ->
            AnimatedVisibility(visible = drawerState.isOpen) {
                Text(
                    "Navegando menú…",
                    modifier = Modifier
                        .padding(8.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            when (selectedPrefItem.value) {
                "Configuración", "Ayuda y soporte" -> {
                    FuncionalityInProgress()
                }

                else -> {
                    TeacherView(innerPadding, teacherViewModel, navController)
                }
            }
        }
    }
}

@Composable
fun DrawerOption(
    label: String,
    icon: ImageVector? = null,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(label) },
        selected = isSelected,
        icon = { icon?.let { Icon(it, contentDescription = null) } },
        onClick = onClick
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TeacherView(padding: PaddingValues, viewModel: TeacherViewModel, navController: NavController) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.isRefreshing,
        onRefresh = { viewModel.fetchCourses() }
    )

    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .pullRefresh(state = pullRefreshState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Card(
                onClick = {
                    navController.navigate("create_content/${viewModel.selectedCourse?.id}")
                },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Agregar contenido",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 24.sp
                    )
                    Icon(
                        imageVector = Icons.Filled.AddCircle,
                        contentDescription = "Agregar contenido",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    if (viewModel.selectedCourse != null) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 16.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                CourseView(viewModel.selectedCourse, true)
                            }
                        }
                    } else {
                        NoCoursesAvailable()
                    }
                }
            }
        }

    }

    PullRefreshIndicator(
        refreshing = viewModel.isRefreshing,
        state = pullRefreshState,
    )
}

@Composable
fun NewCourse(navController: NavController) {
    ExtendedFloatingActionButton(
        onClick = { navController.navigate("add_course") },
        icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
        text = { Text(text = "Crear curso") },
    )
}

@Composable
fun FuncionalityInProgress() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Funcionalidad en desarrollo...",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun NoCoursesAvailable() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("No tienes cursos disponibles todavía.")
    }
}

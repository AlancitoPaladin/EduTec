package com.itsm.edutec.ui.theme.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.itsm.edutec.ui.theme.screens.profiles.student.CourseScreen
import com.itsm.edutec.ui.theme.screens.profiles.student.StudentScreen
import com.itsm.edutec.ui.theme.screens.profiles.teacher.TeacherScreen
import com.itsm.edutec.ui.theme.screens.register.ForgotPassword
import com.itsm.edutec.ui.theme.screens.register.LoginScreen
import com.itsm.edutec.ui.theme.screens.register.RegisterUser

@Composable
fun AppNavigation(paddingValues: PaddingValues) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = Modifier.padding(paddingValues)
    ) {
        composable("login") { LoginScreen(navController) }
        composable("register_user") { RegisterUser(navController) }
        composable("forgot_password") { ForgotPassword(navController) }
        composable("teacher_screen") { TeacherScreen() }
        composable("student_screen") { StudentScreen(navController) }
        composable("courseDetails/{courseId}") { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId") ?: return@composable
            CourseScreen(courseId, navController)
        }
    }
}
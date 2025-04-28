package com.itsm.edutec.ui.theme.models

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.edutec.ui.theme.api.Course
import com.itsm.edutec.ui.theme.api.CourseApiClient
import com.itsm.edutec.ui.theme.session.SessionManager
import kotlinx.coroutines.launch

class TeacherViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _courses = mutableStateListOf<Course>()
    val courses: List<Course> get() = _courses

    var selectedCourse by mutableStateOf<Course?>(null)
        private set

    init {
        fetchCourses()
    }

    fun fetchCourses() {
        viewModelScope.launch {
            try {
                val email = sessionManager.getUserEmail() ?: throw Exception("Usuario no logueado")
                val response =
                    CourseApiClient.courseService.getCoursesByTeacher(TeacherRequest(email))
                _courses.clear()
                _courses.addAll(response)
                selectedCourse = _courses.firstOrNull()
            } catch (e: Exception) {
                Log.e("TeacherViewModel", "Error al traer cursos: ${e.localizedMessage}")
            }
        }
    }

    fun selectedCourse(course: Course) {
        selectedCourse = course
    }

    fun addCourse(course: Course) {
        _courses.add(course)
    }
}

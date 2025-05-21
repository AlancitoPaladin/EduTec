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
        internal set

    var createCourseError by mutableStateOf<String?>(null)
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    init {
        fetchCourses()
    }


    fun fetchCourses() {
        viewModelScope.launch {
            isRefreshing = true
            try {
                val email = sessionManager.getUserEmail() ?: throw Exception("Usuario no logueado")
                val response =
                    CourseApiClient.courseService.getCoursesByTeacher(TeacherRequest(email))
                _courses.clear()
                _courses.addAll(response)
                selectedCourse = _courses.firstOrNull()
            } catch (e: Exception) {
                Log.e("TeacherViewModel", "Error al traer cursos: ${e.localizedMessage}")
            } finally {
                isRefreshing = false
            }
        }
    }


    fun selectedCourse(course: Course) {
        selectedCourse = course
    }

    fun createCourse(
        title: String,
        description: String,
        imageUrl: String,
        category: String,
        level: String,
        start: String,
        end: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val email = sessionManager.getUserEmail() ?: throw Exception("Usuario no logueado")
                val newCourse = Course(
                    id = "",
                    course = title,
                    start = start,
                    end = end,
                    year = 2025,
                    teacherEmail = email,
                    image = imageUrl,
                    stars = 0.0,
                    category = category,
                    description = description,
                    level = level
                )
                val response = CourseApiClient.courseService.createCourse(newCourse)
                if (response.isSuccessful) {
                    response.body()?.let {
                        val createdCourse = it.course
                        _courses.add(createdCourse)
                        onSuccess()
                    }
                } else {
                    createCourseError = "Error del servidor: ${response.code()}"
                }
            } catch (e: Exception) {
                createCourseError = "Error al crear el curso: ${e.localizedMessage}"
            }
        }
    }
}

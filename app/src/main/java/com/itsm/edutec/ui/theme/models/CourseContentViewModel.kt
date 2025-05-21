package com.itsm.edutec.ui.theme.models


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.edutec.ui.theme.api.CourseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CourseContentViewModel(
    private val courseService: CourseService
) : ViewModel() {

    private val _courseContentState =
        MutableStateFlow<CourseContentState>(CourseContentState.Loading)
    val courseContentState: StateFlow<CourseContentState> = _courseContentState

    fun loadCourseContent(courseId: String) {
        viewModelScope.launch {
            _courseContentState.value = CourseContentState.Loading
            try {
                val response = courseService.getCourseContent(courseId)
                if (response.isSuccessful) {
                    val content = response.body()
                    if (content != null) {
                        _courseContentState.value = CourseContentState.Success(content)
                    } else {
                        _courseContentState.value =
                            CourseContentState.Error("Contenido vacío.")
                    }
                } else {
                    _courseContentState.value =
                        CourseContentState.Error("Error en la respuesta: ${response.code()}")
                }
            } catch (e: Exception) {
                _courseContentState.value =
                    CourseContentState.Error("Excepción: ${e.localizedMessage}")
            }
        }
    }
}

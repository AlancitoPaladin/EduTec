package com.itsm.edutec.ui.theme.screens.profiles.teacher.creation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.edutec.ui.theme.api.CourseApiClient
import com.itsm.edutec.ui.theme.screens.profiles.teacher.content.AssignmentRequest
import kotlinx.coroutines.launch

class AssignmentViewModel(
) : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    var creationSuccess by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun createAssignment(
        courseId: String,
        title: String,
        description: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            creationSuccess = false
            errorMessage = null

            try {
                val assignment = AssignmentRequest(title, description)
                CourseApiClient.courseService.createAssignment(courseId, assignment)
                creationSuccess = true
                onSuccess()
            } catch (e: Exception) {
                Log.e("AssignmentVM", "Error creando tarea: ${e.localizedMessage}")
                errorMessage = "No se pudo crear la tarea: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }
}
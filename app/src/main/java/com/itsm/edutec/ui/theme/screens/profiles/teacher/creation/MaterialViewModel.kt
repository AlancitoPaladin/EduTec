package com.itsm.edutec.ui.theme.screens.profiles.teacher.creation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.edutec.ui.theme.api.CourseApiClient
import com.itsm.edutec.ui.theme.screens.profiles.teacher.content.MaterialRequest
import com.itsm.edutec.ui.theme.session.SessionManager
import kotlinx.coroutines.launch

class MaterialViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var creationSuccess by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun createMaterial(
        courseId: String,
        fileName: String,
        fileUrl: String,
        fileType: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            creationSuccess = false
            errorMessage = null

            try {
                val material = MaterialRequest(fileName, fileUrl, fileType)
                CourseApiClient.courseService.createMaterial(courseId, material)
                creationSuccess = true
                onSuccess()
            } catch (e: Exception) {
                Log.e("MaterialVM", "Error creando material: ${e.localizedMessage}")
                errorMessage = "No se pudo crear el material: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }
}
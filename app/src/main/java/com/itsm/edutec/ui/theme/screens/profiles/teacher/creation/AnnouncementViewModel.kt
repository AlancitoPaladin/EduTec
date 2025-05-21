package com.itsm.edutec.ui.theme.screens.profiles.teacher.creation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.edutec.ui.theme.api.CourseApiClient
import com.itsm.edutec.ui.theme.screens.profiles.teacher.content.AnnouncementRequest
import com.itsm.edutec.ui.theme.session.SessionManager
import kotlinx.coroutines.launch

class AnnouncementViewModel(
    private val sessionManager: SessionManager
) : ViewModel() {

    var isLoading by mutableStateOf(false)
        private set

    var creationSuccess by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun createAnnouncement(
        courseId: String,
        title: String,
        content: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            creationSuccess = false
            errorMessage = null

            try {
                val announcement = AnnouncementRequest(title = title, content = content)
                val response =
                    CourseApiClient.courseService.createAnnouncement(courseId, announcement)
                creationSuccess = true
                onSuccess()

            } catch (e: Exception) {
                Log.e("CreateAnnouncementVM", "Error creando anuncio: ${e.localizedMessage}")
                errorMessage = "No se pudo crear el anuncio: ${e.localizedMessage}"
            } finally {
                isLoading = false
            }
        }
    }
}
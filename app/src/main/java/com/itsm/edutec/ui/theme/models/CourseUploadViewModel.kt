package com.itsm.edutec.ui.theme.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itsm.edutec.ui.theme.api.CourseApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

sealed class UploadState {
    object Idle : UploadState()
    object Loading : UploadState()
    data class Success(val fileId: String) : UploadState()
    data class Error(val message: String) : UploadState()
}

class CourseUploadViewModel : ViewModel() {

    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    val uploadState: StateFlow<UploadState> get() = _uploadState

    fun uploadFile(courseId: String, file: File) {
        viewModelScope.launch {
            _uploadState.value = UploadState.Loading
            try {
                val requestFile =
                    file.asRequestBody("application/octet-stream".toMediaTypeOrNull())
                val multipartFile =
                    MultipartBody.Part.createFormData("file", file.name, requestFile)

                val response = CourseApiClient.courseService.uploadFile(courseId, multipartFile)

                if (response.isSuccessful && response.body() != null) {
                    _uploadState.value = UploadState.Success(response.body()!!.file_id)
                } else {
                    _uploadState.value =
                        UploadState.Error("Error subiendo archivo: ${response.message()}")
                }
            } catch (e: Exception) {
                _uploadState.value = UploadState.Error("Excepción: ${e.localizedMessage}")
            }
        }
    }
}

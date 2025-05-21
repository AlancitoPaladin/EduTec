@file:Suppress("DEPRECATION")

package com.itsm.edutec.ui.theme.screens.profiles.teacher.creation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itsm.edutec.ui.theme.models.CourseUploadViewModel
import com.itsm.edutec.ui.theme.models.UploadState
import java.io.File
import java.io.InputStream

@Composable
fun UploadFileScreen(
    courseId: String,
    viewModel: CourseUploadViewModel = viewModel(),
    onFileUploaded: (String) -> Unit
) {
    val context = LocalContext.current
    val uploadState by viewModel.uploadState.collectAsState()

    var selectedFile by remember { mutableStateOf<File?>(null) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val tempFile = File(context.cacheDir, "upload_${System.currentTimeMillis()}")
            inputStream?.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            selectedFile = tempFile
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ClickableText(
            text = AnnotatedString("Seleccionar archivo"),
            onClick = {
                filePickerLauncher.launch("*/*")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                selectedFile?.let { viewModel.uploadFile(courseId, it) }
            },
            enabled = selectedFile != null && uploadState !is UploadState.Loading
        ) {
            Text("Subir archivo")
        }

        Spacer(modifier = Modifier.height(32.dp))

        when (uploadState) {
            is UploadState.Idle -> Text("Listo para subir.")
            is UploadState.Loading -> CircularProgressIndicator()
            is UploadState.Success -> {
                Text("¡Archivo subido! 🎉")
                Text("ID: ${(uploadState as UploadState.Success).fileId}")
                LaunchedEffect(Unit) {
                    onFileUploaded((uploadState as UploadState.Success).fileId)
                }
            }

            is UploadState.Error -> Text(
                "Error: ${(uploadState as UploadState.Error).message}",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}


@Composable
fun OutlinedButtonExample(onClick: () -> Unit) {
    OutlinedButton(onClick = { onClick() }) {
        Text("Outlined")
    }
}

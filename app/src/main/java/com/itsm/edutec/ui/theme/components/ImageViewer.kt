package com.itsm.edutec.ui.theme.components

import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide

@Composable
fun MyGlideImageWithView(imageUrl: String, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            ImageView(context).apply {
                Glide.with(context)
                    .load(imageUrl)
                    .placeholder(android.R.drawable.stat_sys_download)
                    .error(android.R.drawable.stat_notify_error)
                    .into(this)
            }
        },
        modifier = modifier
    )
}
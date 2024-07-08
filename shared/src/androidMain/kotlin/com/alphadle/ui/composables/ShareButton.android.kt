package com.alphadle.ui.composables

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@Composable
internal actual fun ShareButton(
    text: () -> String,
    modifier: Modifier
) {
    val context = LocalContext.current

    Button(
        onClick = {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text())
                type = "text/plain"
            }

            context.startActivity(Intent.createChooser(sendIntent, null))
        },
        modifier = modifier
    ) {
        Text(text = "Share ")
        Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
    }
}
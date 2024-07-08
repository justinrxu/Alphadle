package com.alphadle.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController
import platform.UIKit.UIWindow
import platform.UIKit.popoverPresentationController

@Composable
internal actual fun ShareButton(
    text: () -> String,
    modifier: Modifier
) {
    Button(
        onClick = {
            val activityController = UIActivityViewController(
                activityItems = listOf(text()),
                applicationActivities = null,
            )
            val window = UIApplication.sharedApplication.windows().first() as? UIWindow
            activityController.popoverPresentationController()?.sourceView = window
            activityController.setTitle("Alphadle")
            window?.rootViewController?.presentViewController(
                viewControllerToPresent = activityController as UIViewController,
                animated = true,
                completion = null,
            )
        },
        modifier = modifier
    ) {
        Text(text = "Share ")
        Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
    }
}
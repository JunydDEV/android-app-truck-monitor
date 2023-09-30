package com.truck.monitor.app.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.truck.monitor.app.ui.progressIndicatorTestTag

@Composable
fun ProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(progressIndicatorTestTag),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
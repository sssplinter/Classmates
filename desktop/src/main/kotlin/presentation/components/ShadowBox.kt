package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ShadowBox(onClick: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(enabled = false, onClick = onClick)
        .background(Color.Black.copy(0.2f))) {}
}
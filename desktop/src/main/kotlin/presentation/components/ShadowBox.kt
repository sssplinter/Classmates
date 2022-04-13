package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ShadowBox(onClick: (() -> Unit)? = null) {
    val onClickAction = onClick ?: {}
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(enabled = onClick != null, onClick = onClickAction)
            .background(Color.Black.copy(0.2f))
    ) {}
}
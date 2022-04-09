package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NoSuchAccountDialog(
    backgroundColor: Color,
    shape: Shape,
    message: String,
    buttonText: String,
    onOkClick: () -> Unit,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable(enabled = false, onClick = {})
        .background(Color.Black.copy(0.2f))) {}
    Column(
        modifier = Modifier
            .width(200.dp)
            .clip(shape)
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            text = message,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Divider(modifier = Modifier.fillMaxWidth(), color = Color.Gray.copy(alpha = 0.5f))
        TextButton(onClick = onOkClick, contentPadding = PaddingValues()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buttonText,
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
        }
    }
}
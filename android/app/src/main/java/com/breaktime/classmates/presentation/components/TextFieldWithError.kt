package com.breaktime.classmates.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.breaktime.classmates.localization.Vocabulary
import com.breaktime.classmates.ui.theme.EXTRA_SMALL_PADDING
import com.breaktime.classmates.ui.theme.MEDIUM_PADDING

@Composable
fun TextFieldWithError(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    imageVector: ImageVector? = null,
    errorText: String,
    isError: Boolean,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    Column {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(vertical = EXTRA_SMALL_PADDING),
            value = value,
            isError = isError,
            onValueChange = onValueChange,
            label = { Text(label) },
            leadingIcon = {
                imageVector?.let {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = Vocabulary.localization.emailImgContent
                    )
                }
            },
            trailingIcon = {
                if (isError)
                    Icon(
                        imageVector = Icons.Default.Warning,
                        Vocabulary.localization.errorImgContent,
                        tint = MaterialTheme.colors.error
                    )
            },
            singleLine = true,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )

        if (isError) {
            Text(
                text = errorText,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = MEDIUM_PADDING)
            )
        }
    }
}
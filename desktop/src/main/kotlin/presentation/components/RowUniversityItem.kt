package presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import localization.Vocabulary
import ui.theme.THE_SMALLEST_PADDING

@Composable
fun RowUniversityItem(
    universityName: String,
    speciality: String,
    group: String,
    onEditClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(THE_SMALLEST_PADDING),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "$universityName $speciality",
                fontSize = 14.sp
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buildAnnotatedString {
                    append(Vocabulary.localization.group)
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append(group)
                    }
                },
                fontSize = 12.sp
            )
        }
        Text(
            modifier = Modifier.clickable { onEditClick() },
            text = Vocabulary.localization.editGroup,
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}
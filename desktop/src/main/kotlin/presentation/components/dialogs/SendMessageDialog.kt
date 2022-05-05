package presentation.components.dialogs

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import localization.Vocabulary
import presentation.components.RoundedTextField
import ui.theme.EXTRA_SMALL_PADDING
import ui.theme.MEDIUM_PADDING
import ui.theme.SMALL_PADDING
import util.dp

@Composable
fun SendMessageDialog(onClickCancel: () -> Unit, onClickSend: (String) -> Unit) {
    val messageText = remember { mutableStateOf("") }
    Popup {
        Column(
            modifier = Modifier
                .width(250.dp)
                .animateContentSize()
                .clip(MaterialTheme.shapes.medium)
                .background(Color.White)
                .padding(MEDIUM_PADDING)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = EXTRA_SMALL_PADDING),
                text = Vocabulary.localization.sendMsg,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = SMALL_PADDING),
                text =  Vocabulary.localization.privateMsgSend,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
            )
            RoundedTextField(
                modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray, RoundedCornerShape(5.dp)),
                text = messageText,
                hint =  Vocabulary.localization.message,
            )
            Row(modifier = Modifier.fillMaxWidth().padding(top = MEDIUM_PADDING)) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .height(20.sp.dp)
                        .clickable(onClick = onClickCancel),
                    text =  Vocabulary.localization.cancel,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = Color(0xFF0C4CDD)
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .height(20.sp.dp)
                        .clickable {
                            if (messageText.value.isNotEmpty()) {
                                onClickSend(messageText.value)
                            }
                        },
                    text =  Vocabulary.localization.send,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = Color(0xFF0C4CDD)
                )
            }
        }
    }
}

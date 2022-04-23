package presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO
import org.jetbrains.skia.Image as ImageSkia

@Composable
fun WebImage(
    url: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeHolder: Painter,
) {
    var isLoadedSuccessfully by remember { mutableStateOf(false) }
    val image = remember { mutableStateOf<ImageBitmap?>(null) }
    val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        //println("error on image loading")
    }
    MainScope().launch(exceptionHandler) {
        image.value = loadNetworkImage(url)
        isLoadedSuccessfully = true
    }
    if (isLoadedSuccessfully) {
        image.value?.let { img ->
            Image(
                bitmap = img,
                modifier = modifier,
                contentScale = contentScale,
                contentDescription = null
            )
        }
    } else {
        Image(
            painter = placeHolder,
            modifier = modifier,
            contentScale = contentScale,
            contentDescription = null
        )
    }
}

private fun loadNetworkImage(link: String): ImageBitmap {
    val url = URL(link)
    val fileFormat = link.substring(link.lastIndexOf(".") + 1)
    val connection = url.openConnection() as HttpURLConnection
    connection.connect()

    val inputStream = connection.inputStream
    val bufferedImage = ImageIO.read(inputStream)

    val stream = ByteArrayOutputStream()
    ImageIO.write(bufferedImage, fileFormat, stream)
    val byteArray = stream.toByteArray()

    return ImageSkia.makeFromEncoded(byteArray).toComposeImageBitmap()
}
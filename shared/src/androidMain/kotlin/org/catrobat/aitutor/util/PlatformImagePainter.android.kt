package org.catrobat.aitutor.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import org.catrobat.aitutor.domain.model.AndroidPlatformImage
import org.catrobat.aitutor.domain.model.PlatformImage

@Composable
actual fun platformImagePainter(image: PlatformImage): Painter {
    return rememberDrawablePainter(drawable = (image as AndroidPlatformImage).androidDrawable)
}

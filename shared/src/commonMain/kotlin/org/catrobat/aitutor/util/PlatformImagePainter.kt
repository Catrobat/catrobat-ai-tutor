package org.catrobat.aitutor.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.catrobat.aitutor.domain.model.PlatformImage

@Composable
expect fun platformImagePainter(image: PlatformImage): Painter

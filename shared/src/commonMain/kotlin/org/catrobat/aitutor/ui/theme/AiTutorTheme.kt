package org.catrobat.aitutor.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Applies [colors] to the AI Tutor UI inside [content].
 *
 * Wrapping is optional. Without it the tutor uses [AiTutorColors.default].
 *
 * Example usage:
 * ```
 * AiTutorTheme(colors = AiTutorColors.default().copy(primary = Color(0xFF00E5A0))) {
 *      AiTutorView(
 *          show = showTutor,
 *          onDismissRequest = { showTutor = false },
 *          codeContext = "...",
 *      )
 * }
 * ```
 *
 * @param colors The palette to apply. Defaults to the palette already in effect, so a nested
 * [AiTutorTheme] keeps the colors it does not override.
 * @param content The AI Tutor UI to apply [colors] to.
 */
@Composable
fun AiTutorTheme(
    colors: AiTutorColors = AiTutorTheme.colors,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalAiTutorColors provides colors, content = content)
}

/**
 * Gives composables access to the [AiTutorColors] in effect at the call site, the same way
 * `MaterialTheme` exposes its color scheme.
 *
 * Example usage:
 * ```
 * Text(text = label, color = AiTutorTheme.colors.onSurface)
 * ```
 */
object AiTutorTheme {
    val colors: AiTutorColors
        @Composable @ReadOnlyComposable
        get() = LocalAiTutorColors.current
}

private val LocalAiTutorColors = staticCompositionLocalOf { AiTutorColors.default() }

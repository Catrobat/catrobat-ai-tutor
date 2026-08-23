package org.catrobat.aitutor.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * The colors the AI Tutor draws itself with.
 *
 * Create a palette with [default] and adjust it with [copy], then apply it through [AiTutorTheme]:
 * ```
 * AiTutorTheme(colors = AiTutorColors.default().copy(primary = Color(0xFF00E5A0))) {
 *      AiTutorView(show = showTutor, onDismissRequest = { showTutor = false }, codeContext = code)
 * }
 * ```
 *
 * @property primary The accent color used for calls to action, links and selection indicators.
 * @property onPrimary The content color drawn on top of [primary].
 * @property surface The background of dialogs, cards and the about screen.
 * @property onSurface The primary text and icon color drawn on top of [surface].
 * @property onSurfaceVariant The secondary text and icon color used for supporting copy.
 * @property secondaryContainer The background of pills, chips and menus.
 * @property onSecondaryContainer The content color drawn on top of [secondaryContainer].
 */
@Immutable
class AiTutorColors internal constructor(
    val primary: Color,
    val onPrimary: Color,
    val surface: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
) {
    fun copy(
        primary: Color = this.primary,
        onPrimary: Color = this.onPrimary,
        surface: Color = this.surface,
        onSurface: Color = this.onSurface,
        onSurfaceVariant: Color = this.onSurfaceVariant,
        secondaryContainer: Color = this.secondaryContainer,
        onSecondaryContainer: Color = this.onSecondaryContainer,
    ): AiTutorColors =
        AiTutorColors(
            primary = primary,
            onPrimary = onPrimary,
            surface = surface,
            onSurface = onSurface,
            onSurfaceVariant = onSurfaceVariant,
            secondaryContainer = secondaryContainer,
            onSecondaryContainer = onSecondaryContainer,
        )

    companion object {
        private val Default =
            AiTutorColors(
                primary = Color(0xFFC0C0FF),
                onPrimary = Color(0xFF1A1A1A),
                surface = Color(0xFF1F1F2A),
                onSurface = Color(0xFFE0E0E6),
                onSurfaceVariant = Color(0xFF9E9EAC),
                secondaryContainer = Color(0xFF30303E),
                onSecondaryContainer = Color(0xFFCFCFDC),
            )

        fun default(): AiTutorColors = Default
    }
}

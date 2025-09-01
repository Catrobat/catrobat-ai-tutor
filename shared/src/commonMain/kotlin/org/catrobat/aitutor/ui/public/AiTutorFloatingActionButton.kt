package org.catrobat.aitutor.ui.public

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.catrobat.aitutor.ui.theme.AiTutorColors
import org.catrobat.shared.generated.resources.Res
import org.catrobat.shared.generated.resources.ask_ai_tutor
import org.jetbrains.compose.resources.stringResource

/**
 * A pre-styled `FloatingActionButton` (FAB) designed to launch the AI Tutor.
 *
 * This component provides a consistent look and feel for the AI Tutor's entry point,
 * using the library's theme colors and a distinct icon. It is intended to be the primary
 * button that users click to open the [AiTutorView].
 *
 * Example usage:
 * ```
 * var showTutor by remember { mutableStateOf(false) }
 *
 * Scaffold(
 *      floatingActionButton = {
 *          AiTutorFloatingActionButton(onClick = { showTutor = true })
 *      }
 * ) { paddingValues ->
 *      // Your main screen content
 *
 *      if (showTutor) {
 *          AiTutorView(
 *              show = true,
 *              onDismissRequest = { showTutor = false },
 *              codeContext = "..."
 *          )
 *      }
 * }
 * ```
 *
 * @param onClick The callback lambda to be invoked when the button is clicked. This
 * should typically be used to change a state variable that controls the visibility
 * of the [AiTutorView].
 * @param modifier The [Modifier] to be applied to this FloatingActionButton.
 */
@Composable
fun AiTutorFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        containerColor = AiTutorColors.primary,
        contentColor = AiTutorColors.onPrimary,
        elevation = elevation(defaultElevation = 8.dp),
    ) {
        Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = stringResource(Res.string.ask_ai_tutor),
        )
    }
}

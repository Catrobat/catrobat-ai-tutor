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
        Icon(Icons.Default.AutoAwesome, contentDescription = "Ask AI Tutor")
    }
}

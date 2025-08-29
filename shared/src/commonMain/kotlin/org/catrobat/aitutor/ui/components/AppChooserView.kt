package org.catrobat.aitutor.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.catrobat.aitutor.ui.theme.AiTutorColors
import org.catrobat.aitutor.ui.viewmodel.AiTutorUiState
import org.catrobat.aitutor.util.platformImagePainter

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun AppChooserView(
    modifier: Modifier = Modifier,
    uiState: AiTutorUiState,
    onDismissRequest: () -> Unit,
    onAppSelected: (String?) -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = modifier.fillMaxSize().then(modifier),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Surface(
                modifier =
                    Modifier
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .widthIn(max = 580.dp),
                color = AiTutorColors.surface.copy(alpha = 0.95f),
                shape = RoundedCornerShape(24.dp),
                shadowElevation = 8.dp,
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Choose AI App of your choice",
                        style = MaterialTheme.typography.titleMedium,
                        color = AiTutorColors.onSurface,
                        modifier = Modifier.padding(bottom = 16.dp),
                    )
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = AiTutorColors.primary)
                    } else {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            uiState.installedApps.forEach { app ->
                                AiAppChip(
                                    appInfo = app,
                                    painter = platformImagePainter(app.icon),
                                    onClick = { onAppSelected(app.packageName) },
                                )
                            }
                            MoreChip(onClick = { onAppSelected(null) })
                        }
                    }
                }
            }
        }
    }
}

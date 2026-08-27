package org.catrobat.aitutor.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.catrobat.aitutor.domain.model.AiAppInfo
import org.catrobat.aitutor.ui.theme.AiTutorTheme

@Composable
internal fun AiAppChip(
    appInfo: AiAppInfo,
    painter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable(onClick = onClick)
                .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painter,
            contentDescription = "${appInfo.appName} Icon",
            modifier =
                Modifier
                    .size(56.dp)
                    .clip(CircleShape),
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = appInfo.appName,
            style = MaterialTheme.typography.labelSmall,
            color = AiTutorTheme.colors.onSurface,
            maxLines = 1,
            textAlign = TextAlign.Center,
        )
    }
}

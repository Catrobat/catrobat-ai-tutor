package org.catrobat.aitutor.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.catrobat.aitutor.ui.theme.AiTutorTheme
import org.catrobat.shared.generated.resources.Res
import org.catrobat.shared.generated.resources.more
import org.catrobat.shared.generated.resources.share_with_other_apps
import org.jetbrains.compose.resources.stringResource

@Composable
fun MoreChip(onClick: () -> Unit) {
    Column(
        modifier =
            Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable(onClick = onClick)
                .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier =
                Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onClick)
                    .padding(12.dp),
        ) {
            Icon(
                imageVector = Icons.Default.MoreHoriz,
                contentDescription = stringResource(Res.string.share_with_other_apps),
                tint = AiTutorTheme.colors.onSurfaceVariant,
                modifier = Modifier.fillMaxSize(),
            )
        }

        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.more),
            style = MaterialTheme.typography.labelSmall,
            color = AiTutorTheme.colors.onSurfaceVariant,
        )
    }
}

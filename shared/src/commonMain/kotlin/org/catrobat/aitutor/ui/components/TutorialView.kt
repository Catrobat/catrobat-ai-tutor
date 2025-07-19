package org.catrobat.aitutor.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.catrobat.aitutor.ui.theme.AiTutorColors

@Composable
internal fun TutorialView(onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier =
                Modifier
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .widthIn(max = 580.dp),
            color = AiTutorColors.surface.copy(alpha = 0.95f),
            shape = RoundedCornerShape(24.dp),
            shadowElevation = 8.dp,
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Welcome to the AI Tutor!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = AiTutorColors.onSurface,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Here's how it works:",
                    style = MaterialTheme.typography.titleMedium,
                    color = AiTutorColors.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(24.dp))
                TutorialStep(
                    step = "1. Ask",
                    description = "Type your question. You can also include your current code for better context.",
                )
                Spacer(Modifier.height(16.dp))
                TutorialStep(
                    step = "2. Choose",
                    description = "Select your favorite AI app (like Gemini or ChatGPT) from the list of installed apps.",
                )
                Spacer(Modifier.height(16.dp))
                TutorialStep(
                    step = "3. Launch",
                    description = "We'll prepare a special prompt and launch the AI app for you to get the answer.",
                )
                Spacer(Modifier.height(16.dp))
                TutorialStep(
                    step = "4. Copy & Paste",
                    description = "Copy the code solution from the AI app, then return here to paste it into your project.",
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.buttonColors(containerColor = AiTutorColors.primary),
                ) {
                    Text("Got It!", color = AiTutorColors.onPrimary)
                }
            }
        }
    }
}

@Composable
private fun TutorialStep(
    step: String,
    description: String,
) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = AiTutorColors.primary,
            modifier = Modifier.padding(end = 12.dp, top = 2.dp).size(20.dp),
        )
        Column {
            Text(
                text = step,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AiTutorColors.onSurface,
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = AiTutorColors.onSurfaceVariant,
            )
        }
    }
}

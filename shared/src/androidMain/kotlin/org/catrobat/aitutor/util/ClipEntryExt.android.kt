package org.catrobat.aitutor.util

import android.content.ClipData
import androidx.compose.ui.platform.ClipEntry

actual suspend fun ClipEntry.getText(): String? =
    try {
        buildString {
            for (i in 0 until clipData.itemCount) {
                clipData.getItemAt(i)?.text?.let { append(it) }
            }
        }.ifBlank { null }
    } catch (_: Exception) {
        null
    }

actual fun clipEntryOf(text: String): ClipEntry = ClipEntry(ClipData.newPlainText("code context", text))

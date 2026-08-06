package org.catrobat.aitutor.util

import android.content.ClipData
import androidx.compose.ui.platform.ClipEntry
import org.catrobat.aitutor.domain.model.ClipboardReadResult

actual suspend fun ClipEntry.getText(): ClipboardReadResult =
    try {
        val text =
            buildString {
                for (i in 0 until clipData.itemCount) {
                    clipData.getItemAt(i)?.text?.let { append(it) }
                }
            }
        if (text.isBlank()) {
            ClipboardReadResult.Empty
        } else {
            ClipboardReadResult.Success(text = text)
        }
    } catch (e: Exception) {
        ClipboardReadResult.Error(cause = e)
    }

actual fun clipEntryOf(text: String): ClipEntry = ClipEntry(ClipData.newPlainText("code context", text))

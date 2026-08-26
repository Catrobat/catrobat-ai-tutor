package org.catrobat.aitutor.util

import androidx.compose.ui.platform.ClipEntry
import org.catrobat.aitutor.domain.model.ClipboardReadResult

expect suspend fun ClipEntry.getText(): ClipboardReadResult

expect fun clipEntryOf(text: String): ClipEntry

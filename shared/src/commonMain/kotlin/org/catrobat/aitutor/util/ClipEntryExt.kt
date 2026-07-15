package org.catrobat.aitutor.util

import androidx.compose.ui.platform.ClipEntry

expect suspend fun ClipEntry.getText(): String?

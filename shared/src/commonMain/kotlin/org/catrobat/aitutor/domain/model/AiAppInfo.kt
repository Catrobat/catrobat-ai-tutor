package org.catrobat.aitutor.domain.model

data class AiAppInfo(
    val appName: String,
    val packageName: String,
    val icon: PlatformImage,
)

interface PlatformImage

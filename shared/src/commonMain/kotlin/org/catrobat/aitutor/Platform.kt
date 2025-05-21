package org.catrobat.aitutor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
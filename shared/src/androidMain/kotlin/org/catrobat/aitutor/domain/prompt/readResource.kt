package org.catrobat.aitutor.domain.prompt

import org.catrobat.aitutor.R

actual fun readTextResource(path: String): String {
    val classLoader = Thread.currentThread().contextClassLoader
        ?: object {}.javaClass.classLoader

    val stream = classLoader?.getResourceAsStream(path)
        ?: error("Resource not found: $path")

    return stream.bufferedReader().use { it.readText() }
}

package org.catrobat.aitutor.di

import org.catrobat.aitutor.internal.koin.core.KoinApplication
import kotlin.concurrent.Volatile

internal object AiTutorKoin {
    @Volatile
    private var _app: KoinApplication? = null

    val app: KoinApplication
        get() =
            _app ?: error(
                "AiTutorKoin has not been initialized. Call setApp(...) before accessing app. " +
                    "On Android, ensure the App Startup initializer is enabled and runs before use.",
            )

    fun setApp(koinApplication: KoinApplication) {
        if (_app != null) {
            error("AiTutorKoin has already been initialized. setApp() must only be called once.")
        }
        _app = koinApplication
    }
}

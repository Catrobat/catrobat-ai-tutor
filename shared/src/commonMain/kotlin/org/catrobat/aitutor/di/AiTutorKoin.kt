package org.catrobat.aitutor.di

import embedded.koin.core.KoinApplication

internal object AiTutorKoin {
    lateinit var app: KoinApplication
        private set

    fun setApp(koinApplication: KoinApplication) {
        if (::app.isInitialized) return
        app = koinApplication
    }
}

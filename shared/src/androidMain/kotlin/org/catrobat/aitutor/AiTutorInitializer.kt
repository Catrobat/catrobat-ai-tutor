package org.catrobat.aitutor

import android.content.Context
import org.catrobat.aitutor.data.GeminiApiRepository
import org.catrobat.aitutor.di.commonModule
import org.catrobat.aitutor.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

/**
 * Public initializer for the AI Tutor library.
 * The consuming application must call the init() method once,
 * typically in its Application class.
 */
object AiTutorInitializer {
    /**
     * Initializes the dependency injection framework for the library.
     *
     * @param context The Android Application context.
     * @param geminiApiKey The Gemini API key used for in-app chat capability.
     */
    fun init(context: Context, geminiApiKey: String = "") {
        startKoin {
            androidLogger()
            androidContext(context.applicationContext)
            val dynamicModule = module {
                single { GeminiApiRepository(geminiApiKey) }
            }
            modules(commonModule, platformModule(), dynamicModule)
        }
    }
}

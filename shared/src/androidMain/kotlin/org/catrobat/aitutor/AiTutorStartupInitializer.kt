package org.catrobat.aitutor

import android.content.Context
import androidx.startup.Initializer
import embedded.koin.android.ext.koin.androidContext
import embedded.koin.android.ext.koin.androidLogger
import embedded.koin.core.logger.Level
import embedded.koin.dsl.koinApplication
import org.catrobat.aitutor.di.AiTutorKoin
import org.catrobat.aitutor.di.commonModule
import org.catrobat.aitutor.di.platformModule

class AiTutorStartupInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        AiTutorKoin.setApp(
            koinApplication {
                androidLogger(level = if (BuildConfig.DEBUG) Level.DEBUG else Level.ERROR)
                androidContext(context.applicationContext)
                modules(commonModule, platformModule())
            },
        )
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

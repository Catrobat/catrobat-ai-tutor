package org.catrobat.aitutor

import android.content.Context
import androidx.startup.Initializer
import org.catrobat.aitutor.di.AiTutorKoin
import org.catrobat.aitutor.di.commonModule
import org.catrobat.aitutor.di.platformModule
import org.catrobat.aitutor.internal.koin.android.ext.koin.androidContext
import org.catrobat.aitutor.internal.koin.android.ext.koin.androidLogger
import org.catrobat.aitutor.internal.koin.core.logger.Level
import org.catrobat.aitutor.internal.koin.dsl.koinApplication

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

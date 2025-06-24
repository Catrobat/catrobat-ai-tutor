package org.catrobat.aitutor.di

import org.catrobat.aitutor.data.AndroidAiAppRepository
import org.catrobat.aitutor.data.InstalledAiAppDataSource
import org.catrobat.aitutor.domain.repository.AiAppRepository
import org.catrobat.aitutor.ui.AiAppLauncher
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module =
    module {
        single { InstalledAiAppDataSource(androidContext()) }

        single<AiAppRepository> { AndroidAiAppRepository(get(), androidContext().packageManager) }

        single { AiAppLauncher(androidContext()) }
    }

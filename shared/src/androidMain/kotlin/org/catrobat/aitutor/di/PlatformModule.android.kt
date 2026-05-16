package org.catrobat.aitutor.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import embedded.koin.android.ext.koin.androidContext
import embedded.koin.core.module.Module
import embedded.koin.dsl.module
import org.catrobat.aitutor.data.AndroidAiAppRepository
import org.catrobat.aitutor.data.InstalledAiAppDataSource
import org.catrobat.aitutor.domain.repository.AiAppRepository
import org.catrobat.aitutor.ui.AiAppLauncher

actual fun platformModule(): Module =
    module {
        single { InstalledAiAppDataSource(androidContext()) }

        single<AiAppRepository> { AndroidAiAppRepository(get(), androidContext().packageManager) }

        single { AiAppLauncher(androidContext()) }

        single<DataStore<Preferences>> { createDataStore(androidContext()) }
    }

fun createDataStore(context: Context): DataStore<Preferences> =
    PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile(DATASTORE_FILE_NAME)
    }

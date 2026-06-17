package org.catrobat.aitutor.di

import embedded.koin.core.module.Module

expect fun platformModule(): Module

internal const val DATASTORE_FILE_NAME = "aitutor_settings.preferences_pb"

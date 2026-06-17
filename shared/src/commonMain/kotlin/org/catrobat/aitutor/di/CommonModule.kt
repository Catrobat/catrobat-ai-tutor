package org.catrobat.aitutor.di

import embedded.koin.core.module.dsl.factoryOf
import embedded.koin.dsl.module
import org.catrobat.aitutor.data.DataStoreSettingsRepository
import org.catrobat.aitutor.domain.repository.SettingsRepository
import org.catrobat.aitutor.domain.usecase.CreateShareablePromptUseCase
import org.catrobat.aitutor.domain.usecase.GetInstalledAiAppsUseCase
import org.catrobat.aitutor.domain.usecase.GetTutorialSeenStateUseCase
import org.catrobat.aitutor.domain.usecase.SetTutorialSeenUseCase
import org.catrobat.aitutor.ui.viewmodel.AiTutorViewModel

val commonModule =
    module {
        // Repositories
        single<SettingsRepository> { DataStoreSettingsRepository(get()) }

        // Use cases
        single { GetInstalledAiAppsUseCase(get()) }
        single { CreateShareablePromptUseCase() }
        single { GetTutorialSeenStateUseCase(get()) }
        single { SetTutorialSeenUseCase(get()) }

        // ViewModel
        factoryOf(::AiTutorViewModel)
    }

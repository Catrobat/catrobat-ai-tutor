package org.catrobat.aitutor.di

import org.catrobat.aitutor.data.DataStoreSettingsRepository
import org.catrobat.aitutor.domain.repository.SettingsRepository
import org.catrobat.aitutor.domain.usecase.CreateShareablePromptUseCase
import org.catrobat.aitutor.domain.usecase.GetInstalledAiAppsUseCase
import org.catrobat.aitutor.domain.usecase.GetTutorialSeenStateUseCase
import org.catrobat.aitutor.domain.usecase.SetTutorialSeenUseCase
import org.catrobat.aitutor.data.GeminiApiRepository
import org.catrobat.aitutor.ui.viewmodel.AiTutorViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val commonModule =
    module {
        // Repositories
        single<SettingsRepository> { DataStoreSettingsRepository(get()) }
        single { GeminiApiRepository(get()) }


        // Use cases
        single { GetInstalledAiAppsUseCase(get()) }
        single { CreateShareablePromptUseCase() }
        single { GetTutorialSeenStateUseCase(get()) }
        single { SetTutorialSeenUseCase(get()) }

        // ViewModel
        viewModelOf(::AiTutorViewModel)
    }

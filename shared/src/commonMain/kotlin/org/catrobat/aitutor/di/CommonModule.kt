package org.catrobat.aitutor.di

import org.catrobat.aitutor.data.ComposeResourcePromptTemplateRepository
import org.catrobat.aitutor.data.DataStoreSettingsRepository
import org.catrobat.aitutor.domain.repository.PromptTemplateRepository
import org.catrobat.aitutor.domain.repository.SettingsRepository
import org.catrobat.aitutor.domain.usecase.CreateShareablePromptUseCase
import org.catrobat.aitutor.domain.usecase.GetInstalledAiAppsUseCase
import org.catrobat.aitutor.domain.usecase.GetTutorialSeenStateUseCase
import org.catrobat.aitutor.domain.usecase.SetTutorialSeenUseCase
import org.catrobat.aitutor.internal.koin.core.module.dsl.factoryOf
import org.catrobat.aitutor.internal.koin.dsl.module
import org.catrobat.aitutor.ui.viewmodel.AiTutorViewModel

val commonModule =
    module {
        // Repositories
        single<SettingsRepository> { DataStoreSettingsRepository(get()) }
        single<PromptTemplateRepository> { ComposeResourcePromptTemplateRepository() }

        // Use cases
        single { GetInstalledAiAppsUseCase(get()) }
        single { CreateShareablePromptUseCase(get()) }
        single { GetTutorialSeenStateUseCase(get()) }
        single { SetTutorialSeenUseCase(get()) }

        // ViewModel
        factoryOf(::AiTutorViewModel)
    }

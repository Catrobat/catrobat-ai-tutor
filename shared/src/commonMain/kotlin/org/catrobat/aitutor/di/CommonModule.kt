package org.catrobat.aitutor.di

import org.catrobat.aitutor.domain.usecase.CreateShareablePromptUseCase
import org.catrobat.aitutor.domain.usecase.GetInstalledAiAppsUseCase
import org.catrobat.aitutor.ui.viewmodel.AiTutorViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val commonModule =
    module {
        // Use cases
        single { GetInstalledAiAppsUseCase(get()) }
        single { CreateShareablePromptUseCase() }

        // ViewModel
        viewModelOf(::AiTutorViewModel)
    }

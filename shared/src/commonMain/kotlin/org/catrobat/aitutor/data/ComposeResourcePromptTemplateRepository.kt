package org.catrobat.aitutor.data

import org.catrobat.aitutor.domain.repository.PromptTemplateRepository
import org.catrobat.shared.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

internal class ComposeResourcePromptTemplateRepository : PromptTemplateRepository {
    @OptIn(ExperimentalResourceApi::class)
    override suspend fun getTemplate(fileName: String): String = Res.readBytes("files/prompts/$fileName").decodeToString()
}

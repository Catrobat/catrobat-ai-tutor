package org.catrobat.aitutor.domain.usecase

import org.catrobat.aitutor.domain.prompt.PromptVersion
import org.catrobat.aitutor.domain.repository.PromptTemplateRepository

class CreateShareablePromptUseCase(
    private val promptTemplateRepository: PromptTemplateRepository,
) {
    suspend operator fun invoke(
        userQuestion: String,
        isCodeContextIncluded: Boolean,
        codeContext: String?,
        isOutputContextIncluded: Boolean? = null,
        outputContext: String? = null,
        promptVersion: PromptVersion,
        systemContext: String? = null,
    ): String {
        val strategy = promptVersion.strategy
        val template = promptTemplateRepository.getTemplate(strategy.templateFileName)
        return strategy.createPrompt(
            template = template,
            userQuestion = userQuestion,
            isCodeContextIncluded = isCodeContextIncluded,
            codeContext = codeContext,
            isOutputContextIncluded = isOutputContextIncluded,
            outputContext = outputContext,
            systemContext = systemContext,
        )
    }
}

package org.catrobat.aitutor.domain.usecase

import org.catrobat.aitutor.domain.prompt.PromptVersion

class CreateShareablePromptUseCase {
    operator fun invoke(
        userQuestion: String,
        isCodeContextIncluded: Boolean,
        codeContext: String?,
        isOutputContextIncluded: Boolean? = null,
        outputContext: String? = null,
        promptVersion: PromptVersion,
    ): String {
        return promptVersion.strategy.createPrompt(
            userQuestion = userQuestion,
            isCodeContextIncluded = isCodeContextIncluded,
            codeContext = codeContext,
            isOutputContextIncluded = isOutputContextIncluded,
            outputContext = outputContext,
        )
    }
}

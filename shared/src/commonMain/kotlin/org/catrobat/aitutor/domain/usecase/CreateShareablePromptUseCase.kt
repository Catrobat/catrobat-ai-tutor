package org.catrobat.aitutor.domain.usecase

class CreateShareablePromptUseCase {
    operator fun invoke(
        userQuestion: String,
        isCodeContextIncluded: Boolean,
        codeContext: String?,
    ): String {
        if (!isCodeContextIncluded || codeContext.isNullOrBlank()) {
            return userQuestion
        }

        // For now, we'll use a simple template, next week we will improve it
        return """
            I have a question about my code.

            My Question:
            $userQuestion

            My Code:
            ```
            $codeContext
            ```
            """.trimIndent()
    }
}

package org.catrobat.aitutor.domain.usecase

class CreateShareablePromptUseCase {
    operator fun invoke(
        userQuestion: String,
        isCodeContextIncluded: Boolean,
        codeContext: String?,
    ): String {
        val contextSection =
            if (isCodeContextIncluded && !codeContext.isNullOrBlank()) {
                "**User Code:**\n```\n$codeContext\n```"
            } else {
                ""
            }

        return """
            **Analyze the following programming question. Provide a direct code solution first, then a brief explanation.**
                        
            **User Question:** $userQuestion
            $contextSection

            **Required Response Format:**
            1. **Corrected Code:** Put the corrected code directly between the following markers inside a markdown block.
            2. **Explanation:** After the code block, briefly explain the solution or the fix.

            ### START - COPY THIS CODE BACK TO THE APP ###
            [Your corrected code here]
            ### END - COPY THIS CODE BACK TO THE APP ###
            """.trimIndent()
    }
}

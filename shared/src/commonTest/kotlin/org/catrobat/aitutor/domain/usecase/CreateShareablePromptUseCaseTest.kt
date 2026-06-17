package org.catrobat.aitutor.domain.usecase

import org.catrobat.aitutor.domain.prompt.PromptVersion
import org.catrobat.aitutor.domain.prompt.V1PromptStrategy
import kotlin.test.Test
import kotlin.test.assertEquals

class CreateShareablePromptUseCaseTest {
    private val useCase = CreateShareablePromptUseCase()

    @Test
    fun `invoke should use V1 strategy and return the correctly formatted prompt`() {
        val userQuestion = "How does this loop work?"
        val codeContext = "for i in 1..10 { print(i) }"
        val outputContext = "12345678910"
        val systemContext = "You are a programming expert."

        val expectedPrompt =
            V1PromptStrategy().createPrompt(
                userQuestion = userQuestion,
                isCodeContextIncluded = true,
                codeContext = codeContext,
                isOutputContextIncluded = true,
                outputContext = outputContext,
                systemContext = systemContext,
            )

        val result =
            useCase(
                userQuestion = userQuestion,
                isCodeContextIncluded = true,
                codeContext = codeContext,
                isOutputContextIncluded = true,
                outputContext = outputContext,
                promptVersion = PromptVersion.V1,
                systemContext = systemContext,
            )

        assertEquals(expectedPrompt, result)
    }

    @Test
    fun `invoke should handle null and false contexts correctly with V1 strategy`() {
        val userQuestion = "Why is my app crashing?"
        val codeContext = "val x = null; x!!.toString()"

        val expectedPrompt =
            V1PromptStrategy().createPrompt(
                userQuestion = userQuestion,
                isCodeContextIncluded = false,
                codeContext = codeContext,
                isOutputContextIncluded = null,
                outputContext = null,
                systemContext = null,
            )

        val result =
            useCase(
                userQuestion = userQuestion,
                isCodeContextIncluded = false,
                codeContext = codeContext,
                isOutputContextIncluded = null,
                outputContext = null,
                promptVersion = PromptVersion.V1,
                systemContext = null,
            )

        assertEquals(expectedPrompt, result)
    }
}

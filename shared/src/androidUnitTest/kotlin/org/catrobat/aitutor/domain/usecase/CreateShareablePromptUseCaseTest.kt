package org.catrobat.aitutor.domain.usecase

import kotlinx.coroutines.test.runTest
import org.catrobat.aitutor.data.ComposeResourcePromptTemplateRepository
import org.catrobat.aitutor.domain.prompt.PromptVersion
import org.catrobat.aitutor.domain.prompt.V1PromptStrategy
import org.catrobat.aitutor.initComposeResourcesContext
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class CreateShareablePromptUseCaseTest {
    private val templateRepository = ComposeResourcePromptTemplateRepository()
    private val useCase = CreateShareablePromptUseCase(templateRepository)

    @BeforeTest
    fun setUp() {
        initComposeResourcesContext()
    }

    @Test
    fun `invoke should use V1 strategy and return the correctly formatted prompt`() =
        runTest {
            val userQuestion = "How does this loop work?"
            val codeContext = "for i in 1..10 { print(i) }"
            val outputContext = "12345678910"
            val systemContext = "You are a programming expert."

            val strategy = V1PromptStrategy()
            val expectedPrompt =
                strategy.createPrompt(
                    template = templateRepository.getTemplate(strategy.templateFileName),
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
    fun `invoke should handle null and false contexts correctly with V1 strategy`() =
        runTest {
            val userQuestion = "Why is my app crashing?"
            val codeContext = "val x = null; x!!.toString()"

            val strategy = V1PromptStrategy()
            val expectedPrompt =
                strategy.createPrompt(
                    template = templateRepository.getTemplate(strategy.templateFileName),
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

package org.catrobat.aitutor.domain.prompt

import kotlinx.coroutines.test.runTest
import org.catrobat.aitutor.data.ComposeResourcePromptTemplateRepository
import org.catrobat.aitutor.initComposeResourcesContext
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse

@RunWith(RobolectricTestRunner::class)
class PromptReplacementTest {
    private val templateRepository = ComposeResourcePromptTemplateRepository()

    private val userQuestion = "How does this loop work?"
    private val codeContext = "for (i in 1..10) { println(i) }"
    private val outputContext = "12345678910"
    private val systemContext = "You are a helpful coding tutor."

    private val placeholders =
        listOf(
            "\$userQuestion",
            "\$systemContextSection",
            "\$codeContextSection",
            "\$outputContextSection",
            "\$spriteXmlSection",
        )

    @BeforeTest
    fun setUp() {
        initComposeResourcesContext()
    }

    @Test
    fun `every prompt replaces all placeholders`() =
        runTest {
            PromptVersion.entries.forEach { version ->
                val template = templateRepository.getTemplate(version.strategy.templateFileName)
                val result =
                    version.strategy.createPrompt(
                        template = template,
                        userQuestion = userQuestion,
                        isCodeContextIncluded = true,
                        codeContext = codeContext,
                        isOutputContextIncluded = true,
                        outputContext = outputContext,
                        systemContext = systemContext,
                    )

                placeholders.forEach { placeholder ->
                    assertFalse(
                        result.contains(placeholder),
                        "Placeholder $placeholder was not replaced for $version:\n$result",
                    )
                }
            }
        }
}

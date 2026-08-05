package org.catrobat.aitutor.domain.prompt

import kotlinx.coroutines.test.runTest
import org.catrobat.aitutor.data.ComposeResourcePromptTemplateRepository
import org.catrobat.aitutor.initComposeResourcesContext
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class EmbroideryDesignerSpriteEditorPromptStrategyTest {
    private val strategy = EmbroideryDesignerSpriteEditorPromptStrategy()
    private val templateRepository = ComposeResourcePromptTemplateRepository()

    @BeforeTest
    fun setUp() {
        initComposeResourcesContext()
    }

    private suspend fun template() = templateRepository.getTemplate(strategy.templateFileName)

    private val userQuestion = "Make the needle stitch a 10 step square"
    private val spriteXml = "SPRITE_XML_SENTINEL"
    private val outputContext = "OUTPUT_SENTINEL"
    private val systemContext = "SYSTEM_SENTINEL"

    @Test
    fun `format includes all sections when contexts are enabled and non-blank`() =
        runTest {
            val result =
                strategy.createPrompt(
                    template = template(),
                    userQuestion = userQuestion,
                    isCodeContextIncluded = true,
                    codeContext = spriteXml,
                    isOutputContextIncluded = true,
                    outputContext = outputContext,
                    systemContext = systemContext,
                )

            // Static scaffolding is always present
            assertTrue(result.contains("<identity>"))
            assertTrue(result.contains("<embroidery_reference>"))
            assertTrue(result.contains("<response_guidance>"))

            // User question is included
            assertTrue(result.contains(userQuestion))

            // Sprite XML section, with the xml fence and payload
            assertTrue(result.contains("**Current Sprite XML:**"))
            assertTrue(result.contains("```xml"))
            assertTrue(result.contains(spriteXml))

            // Output context section and payload
            assertTrue(result.contains("**Runtime Output / Error:**"))
            assertTrue(result.contains(outputContext))

            // System context section and payload
            assertTrue(result.contains("**Project / Scene Context:**"))
            assertTrue(result.contains(systemContext))

            // Copy-back markers
            assertTrue(result.contains("### START - COPY THIS XML BACK TO EMBROIDERY DESIGNER ###"))
            assertTrue(result.contains("### END - COPY THIS XML BACK TO EMBROIDERY DESIGNER ###"))
        }

    @Test
    fun `format omits optional sections when disabled even with non-null payloads`() =
        runTest {
            val result =
                strategy.createPrompt(
                    template = template(),
                    userQuestion = userQuestion,
                    isCodeContextIncluded = false,
                    codeContext = spriteXml,
                    isOutputContextIncluded = false,
                    outputContext = outputContext,
                    systemContext = null,
                )

            // Optional section headers are absent.
            assertFalse(result.contains("**Current Sprite XML:**"))
            assertFalse(result.contains("**Runtime Output / Error:**"))
            assertFalse(result.contains("**Project / Scene Context:**"))

            // The gated payloads are absent (flags, not just nullness, gate inclusion).
            assertFalse(result.contains(spriteXml))
            assertFalse(result.contains(outputContext))

            // Static scaffolding and the user question remain.
            assertTrue(result.contains("<identity>"))
            assertTrue(result.contains("<embroidery_reference>"))
            assertTrue(result.contains(userQuestion))
        }

    @Test
    fun `format treats blank contexts as omitted even when flags are enabled`() =
        runTest {
            val result =
                strategy.createPrompt(
                    template = template(),
                    userQuestion = userQuestion,
                    isCodeContextIncluded = true,
                    codeContext = "",
                    isOutputContextIncluded = true,
                    outputContext = "   ",
                    systemContext = "",
                )

            assertFalse(result.contains("**Current Sprite XML:**"))
            assertFalse(result.contains("**Runtime Output / Error:**"))
            assertFalse(result.contains("**Project / Scene Context:**"))

            // Static scaffolding and the user question remain.
            assertTrue(result.contains("<embroidery_reference>"))
            assertTrue(result.contains(userQuestion))
        }
}

package org.catrobat.aitutor.domain.prompt

interface PromptStrategy {
    val templateFileName: String

    fun createPrompt(
        template: String,
        userQuestion: String,
        isCodeContextIncluded: Boolean,
        codeContext: String?,
        isOutputContextIncluded: Boolean? = null,
        outputContext: String? = null,
        systemContext: String? = null,
    ): String
}

internal class V1PromptStrategy : PromptStrategy {
    override val templateFileName = "v1-prompt.md"

    override fun createPrompt(
        template: String,
        userQuestion: String,
        isCodeContextIncluded: Boolean,
        codeContext: String?,
        isOutputContextIncluded: Boolean?,
        outputContext: String?,
        systemContext: String?,
    ): String {
        val codeContextSection =
            if (isCodeContextIncluded && !codeContext.isNullOrBlank()) {
                "**User Code:**\n```\n$codeContext\n```"
            } else {
                ""
            }

        val outputContextSection =
            if (isOutputContextIncluded == true && !outputContext.isNullOrBlank()) {
                "**Code Output:**\n```\n$outputContext\n```"
            } else {
                ""
            }

        val systemContextSection =
            if (!systemContext.isNullOrBlank()) {
                "**System Context:**\n$systemContext\n"
            } else {
                ""
            }

        return template
            .replace("\$systemContextSection", systemContextSection)
            .replace("\$userQuestion", userQuestion)
            .replace("\$codeContextSection", codeContextSection)
            .replace("\$outputContextSection", outputContextSection)
            .trimIndent()
    }
}

internal class V2PromptStrategy : PromptStrategy {
    override val templateFileName = "v2-prompt.md"

    override fun createPrompt(
        template: String,
        userQuestion: String,
        isCodeContextIncluded: Boolean,
        codeContext: String?,
        isOutputContextIncluded: Boolean?,
        outputContext: String?,
        systemContext: String?,
    ): String {
        val codeContextSection =
            if (isCodeContextIncluded && !codeContext.isNullOrBlank()) {
                "**User Code:**\n```\n$codeContext\n```"
            } else {
                ""
            }

        val outputContextSection =
            if (isOutputContextIncluded == true && !outputContext.isNullOrBlank()) {
                "**Code Output:**\n```\n$outputContext\n```"
            } else {
                ""
            }

        val systemContextSection =
            if (!systemContext.isNullOrBlank()) {
                "**System Context:**\n$systemContext\n"
            } else {
                ""
            }

        return template
            .replace("\$systemContextSection", systemContextSection)
            .replace("\$userQuestion", userQuestion)
            .replace("\$codeContextSection", codeContextSection)
            .replace("\$outputContextSection", outputContextSection)
            .trimIndent()
    }
}

internal class V3PromptStrategy : PromptStrategy {
    override val templateFileName = "v3-prompt.md"

    override fun createPrompt(
        template: String,
        userQuestion: String,
        isCodeContextIncluded: Boolean,
        codeContext: String?,
        isOutputContextIncluded: Boolean?,
        outputContext: String?,
        systemContext: String?,
    ): String {
        val codeContextSection =
            if (isCodeContextIncluded && !codeContext.isNullOrBlank()) {
                "**Code Context:**\n```\n$codeContext\n```"
            } else {
                ""
            }

        val outputContextSection =
            if (isOutputContextIncluded == true && !outputContext.isNullOrBlank()) {
                "**Last Code Output:**\n```\n$outputContext\n```"
            } else {
                ""
            }

        val systemContextSection =
            if (!systemContext.isNullOrBlank()) {
                "**System Context:**\n$systemContext\n"
            } else {
                ""
            }

        return template
            .replace("\$systemContextSection", systemContextSection)
            .replace("\$userQuestion", userQuestion)
            .replace("\$codeContextSection", codeContextSection)
            .replace("\$outputContextSection", outputContextSection)
            .trimIndent()
    }
}

internal class V4PromptStrategy : PromptStrategy {
    override val templateFileName = "v4-prompt.md"

    override fun createPrompt(
        template: String,
        userQuestion: String,
        isCodeContextIncluded: Boolean,
        codeContext: String?,
        isOutputContextIncluded: Boolean?,
        outputContext: String?,
        systemContext: String?,
    ): String {
        val codeContextSection =
            if (isCodeContextIncluded && !codeContext.isNullOrBlank()) {
                "**Existing Codebase:**\n```\n$codeContext\n```"
            } else {
                ""
            }

        val outputContextSection =
            if (isOutputContextIncluded == true && !outputContext.isNullOrBlank()) {
                "**Output Context:**\n```\n$outputContext\n```"
            } else {
                ""
            }

        val systemContextSection =
            if (!systemContext.isNullOrBlank()) {
                "**System Context:**\n$systemContext\n"
            } else {
                ""
            }

        return template
            .replace("\$systemContextSection", systemContextSection)
            .replace("\$userQuestion", userQuestion)
            .replace("\$codeContextSection", codeContextSection)
            .replace("\$outputContextSection", outputContextSection)
            .trimIndent()
    }
}

internal class PocketCodeSpriteEditorPromptStrategy : PromptStrategy {
    override val templateFileName = "pocketcode-sprite-editor-prompt.md"

    override fun createPrompt(
        template: String,
        userQuestion: String,
        isCodeContextIncluded: Boolean,
        codeContext: String?,
        isOutputContextIncluded: Boolean?,
        outputContext: String?,
        systemContext: String?,
    ): String {
        val spriteXmlSection =
            if (isCodeContextIncluded && !codeContext.isNullOrBlank()) {
                "**Current Sprite XML:**\n```xml\n$codeContext\n```"
            } else {
                ""
            }

        val outputContextSection =
            if (isOutputContextIncluded == true && !outputContext.isNullOrBlank()) {
                "**Runtime Output / Error:**\n```\n$outputContext\n```"
            } else {
                ""
            }

        val systemContextSection =
            if (!systemContext.isNullOrBlank()) {
                "**Project / Scene Context:**\n$systemContext\n"
            } else {
                ""
            }

        return template
            .replace("\$systemContextSection", systemContextSection)
            .replace("\$userQuestion", userQuestion)
            .replace("\$spriteXmlSection", spriteXmlSection)
            .replace("\$outputContextSection", outputContextSection)
            .trimIndent()
    }
}

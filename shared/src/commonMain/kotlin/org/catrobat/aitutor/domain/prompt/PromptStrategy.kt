package org.catrobat.aitutor.domain.prompt

interface PromptStrategy {
    fun createPrompt(
        userQuestion: String,
        isCodeContextIncluded: Boolean,
        codeContext: String?,
        isOutputContextIncluded: Boolean? = null,
        outputContext: String? = null,
    ): String
}

internal class V1PromptStrategy : PromptStrategy {
    override fun createPrompt(
        userQuestion: String,
        isCodeContextIncluded: Boolean,
        codeContext: String?,
        isOutputContextIncluded: Boolean?,
        outputContext: String?,
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

        return """
            **Analyze the following programming question. Provide a direct code solution first, then a brief explanation.**
                        
            **User Question:** $userQuestion
            $codeContextSection
            $outputContextSection

            **Required Response Format:**
            1. **Corrected Code:** Put the corrected code directly between the following markers inside a markdown block.
            2. **Explanation:** After the code block, briefly explain the solution or the fix.

            ### START - COPY THIS CODE BACK TO THE APP ###
            [Your corrected code here]
            ### END - COPY THIS CODE BACK TO THE APP ###
            """.trimIndent()
    }
}

internal class V2PromptStrategy : PromptStrategy {
    override fun createPrompt(
        userQuestion: String,
        isCodeContextIncluded: Boolean,
        codeContext: String?,
        isOutputContextIncluded: Boolean?,
        outputContext: String?,
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

        return """
            <identity>
            You are an AI programming assistant.
            Follow the user's requirements carefully & to the letter. Keep your answers short and impersonal.
            You are a highly sophisticated automated coding agent with expert-level knowledge across many different programming languages and frameworks.
            </identity>

            <instructions>
            The user will ask a question, or ask you to perform a task.
            Your goal is to provide a corrected code solution and a brief explanation.
            Do not make assumptions about the situation. Analyze the provided context (code, errors, output) to inform your answer.
            </instructions>
            
            **User Request:** $userQuestion
            $codeContextSection
            $outputContextSection

            **Required Response Format:**
            1. **Corrected Code:** Put the corrected code directly between the following markers inside a markdown block.
            2. **Explanation:** After the code block, briefly explain the solution or the fix.
            ### START - COPY THIS CODE BACK TO THE APP ###
            [Your corrected code here]
            ### END - COPY THIS CODE BACK TO THE APP ###
            """.trimIndent()
    }
}

internal class V3PromptStrategy : PromptStrategy {
    override fun createPrompt(
        userQuestion: String,
        isCodeContextIncluded: Boolean,
        codeContext: String?,
        isOutputContextIncluded: Boolean?,
        outputContext: String?,
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

        return """
            You are an AI coding assistant. You are pair programming with a USER to solve their coding task.
            Your main goal is to follow the USER's instructions at each message. Analyze the provided context to solve the user's coding task.

            **<user_query>**
            $userQuestion
            **</user_query>**

            <additional_data>
            $codeContextSection
            $outputContextSection
            </additional_data>

            **Required Response Format:**
            1. **Corrected Code:** Provide the complete, corrected code block between the specified markers.
            2. **Explanation:** After the code, provide a brief explanation of the changes.
            ### START - COPY THIS CODE BACK TO THE APP ###
            [Your corrected code here]
            ### END - COPY THIS CODE BACK TO THE APP ###
            """.trimIndent()
    }
}

internal class V4PromptStrategy : PromptStrategy {
    override fun createPrompt(
        userQuestion: String,
        isCodeContextIncluded: Boolean,
        codeContext: String?,
        isOutputContextIncluded: Boolean?,
        outputContext: String?,
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

        return """
            <identity>
            You are a powerful agentic AI coding assistant. You are pair programming with the user to solve their coding task. The task may require creating a new codebase, modifying or debugging an existing codebase, or simply answering a question.
            </identity>

            <purpose>
            The user has a coding task to accomplish. Please take a look at the task and any provided context (code, errors, output). Your goal is to respond directly with a solution.
            </purpose>

            **User Task:**
            $userQuestion
            
            $codeContextSection
            $outputContextSection
            
            <guidelines>
            Analyze the user's request and provide a corrected code block that solves the problem, followed by a short explanation.
            </guidelines>

            **Required Response Format:**
            1. **Corrected Code:** Put the corrected code directly between the following markers inside a markdown block.
            2. **Explanation:** After the code block, briefly explain the solution or the fix.
            ### START - COPY THIS CODE BACK TO THE APP ###
            [Your corrected code here]
            ### END - COPY THIS CODE BACK TO THE APP ###
            """.trimIndent()
    }
}

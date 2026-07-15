package org.catrobat.aitutor.domain.repository

interface PromptTemplateRepository {
    suspend fun getTemplate(fileName: String): String
}

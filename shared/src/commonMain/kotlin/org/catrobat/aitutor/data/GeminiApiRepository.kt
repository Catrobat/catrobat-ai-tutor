package org.catrobat.aitutor.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class GeminiApiRepository(
    private val apiKeyStore: ApiKeyStore,
    private val httpClient: HttpClient = HttpClient(),
) {
    suspend fun sendMessage(prompt: String): Result<String> {
        val apiKey = apiKeyStore.getApiKey() ?: return Result.failure(Exception("API Key not found"))
        return try {
            val response: String =
                httpClient.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey") {
                contentType(ContentType.Application.Json)
                setBody(
                    """
                    {
                      "contents": [{
                        "parts": [{"text": "${prompt.replace("\"", "\\\"").replace("\n", "\\n")}"}]
                      }]
                    }
                    """.trimIndent()
                )
            }.body()

            val jsonResponse = Json.parseToJsonElement(response).jsonObject
            val text = jsonResponse["candidates"]
                ?.jsonArray?.get(0)
                ?.jsonObject?.get("content")
                ?.jsonObject?.get("parts")
                ?.jsonArray?.get(0)
                ?.jsonObject?.get("text")
                ?.jsonPrimitive?.content ?: ""

            Result.success(text)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

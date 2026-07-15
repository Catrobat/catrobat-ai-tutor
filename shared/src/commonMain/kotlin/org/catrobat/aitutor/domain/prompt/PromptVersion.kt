package org.catrobat.aitutor.domain.prompt

enum class PromptVersion(val displayName: String, val strategy: PromptStrategy) {
    V1("Default Prompt", V1PromptStrategy()),
    V2("VSCode/Copilot Style", V2PromptStrategy()),
    V3("Cursor AI Style", V3PromptStrategy()),
    V4("Trae AI Style", V4PromptStrategy()),
    POCKET_CODE_SPRITE("PocketCode Sprite", PocketCodeSpriteEditorPromptStrategy()),
}

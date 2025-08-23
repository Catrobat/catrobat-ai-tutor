# Catrobat AI Tutor

A standalone AI Tutor library for Catrobat and other Android apps.

## Features

- Detect installed AI apps on the user’s device.
- `AiTutorView` composable with tutorial, input field, and copy-paste fallback.
- Pre-styled `AiTutorFloatingActionButton` to launch the tutor.
- Dynamic UI for including optional context (e.g., compiler errors).
- Kotlin Multiplatform (Android working, iOS planned).

## Getting Started

The library will be published to **Maven Central**.  
For now, include it via local publishing.

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("org.catrobat:ai-tutor:<latest-version>")
}
```

Replace `<latest-version>` with the current release.

## Usage

```kotlin
var showTutor by remember { mutableStateOf(false) }

Scaffold(
    floatingActionButton = {
        AiTutorFloatingActionButton(onClick = { showTutor = true })
    }
) { paddingValues ->
    // Your screen content here...

    if (showTutor) {
        AiTutorView(
            show = true,
            onDismissRequest = { showTutor = false },
            codeContext = "...",
//            outputContext = "..."
        )
    }
}
```
- `AiTutorFloatingActionButton` → a pre-styled button to launch the tutor.
- `AiTutorView` → the actual tutor dialog UI. You can also embed it directly in your screen if you don’t want the FAB.
- `codeContext` → send the current code snippet to provide context for the AI.
- `outputContext` → optional, send extra info (like compiler errors) to help the AI provide better answers.


## License

This project is licensed under the GNU Affero General Public License v3.0. See
the [LICENSE](LICENSE) file for details.
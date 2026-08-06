# Catrobat AI Tutor

A standalone AI Tutor library for Catrobat and other Android apps.

## Features

- Detect installed AI apps on the user’s device.
- `AiTutorView` composable with tutorial, input field, and copy-paste fallback.
- Pre-styled `AiTutorFloatingActionButton` to launch the tutor.
- Dynamic UI for including optional context (e.g., compiler errors).
- Kotlin Multiplatform (Android working, iOS planned).

## Getting Started

Ensure the Kotzilla repository is configured in your project's `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://repository.kotzilla.io/repository/Koin-Embedded/") }
    }
}
```

Then add the dependency to your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("org.catrobat:aitutor:<latest-version>")
}
```

> The library will be published to Maven Central. For now, use local publishing via `./gradlew publishToMavenLocal`.

## Usage

```kotlin
var showTutor by remember { mutableStateOf(false) }

Scaffold(
    floatingActionButton = {
        AiTutorFloatingActionButton(onClick = { showTutor = true })
    }
) { paddingValues ->
    // Your screen content here...

    AiTutorView(
        show = showTutor,
        onDismissRequest = { showTutor = false },
        codeContext = "...",
//      outputContext = "..."
//      systemContext - "..."
        onClipboardPaste = { pastedText ->
            // The AI's answer copied back from the external app — validate/apply it.
            applyAiResult(pastedText)
        },
        onError = { error ->
            Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
        },
      )
}
```
- `AiTutorFloatingActionButton` → a pre-styled button to launch the tutor.
- `AiTutorView` → the actual tutor dialog UI. You can also embed it directly in your screen if you don’t want the FAB.
- `codeContext` → send the current code snippet to provide context for the AI.
- `outputContext` → optional, send extra info (like compiler errors) to help the AI provide better answers.
- `systemContext` → optional, provide additional context, such as the programming language, version, or framework being used.
- `onClipboardPaste` → optional, enables the paste-back flow. When provided, the tutor shows a "Paste AI Answer" dialog after the user returns from the AI app and delivers the clipboard text to this callback.
- `onError` → optional, invoked when the tutor hits a recoverable error (failed to load AI apps, empty-clipboard paste, or an app-launch problem). It receives an `AiTutorError` with a `type` (`AiTutorErrorType`) and a localized `message`.

## Dependencies

This SDK uses Koin internally (embedded version) for dependency injection.
The embedded version is isolated and will not conflict with Koin usage
in your application.

- **Internal dependency:** `embedded-koin-core:3.5.6`
- **Package namespace:** `embedded.koin.*`

## License

This project is licensed under the GNU Affero General Public License v3.0. See
the [LICENSE](LICENSE) file for details.
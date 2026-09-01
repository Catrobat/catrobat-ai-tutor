# Catrobat AI Tutor

A standalone AI Tutor library for Catrobat and other Android apps.

## Features

- Detect installed AI apps on the user’s device.
- `AiTutorView` composable with tutorial, input field, and copy-paste fallback.
- Pre-styled `AiTutorFloatingActionButton` to launch the tutor.
- Dynamic UI for including optional context (e.g., compiler errors).
- `AiTutorTheme` to restyle the tutor with your own colors.
- Kotlin Multiplatform (Android working, iOS planned).

## Getting Started

During development the library is consumed from your local Maven repository. Released builds are distributed as an `.aar` file, see [Releasing](#releasing).

1. Publish the library to Maven Local:

   ```bash
   # Fixed version (matches `version` in shared/build.gradle.kts)
   ./gradlew publishToMavenLocal

   # Or the -LOCAL version, for iterative development
   ./gradlew publishToMavenLocal -Psnapshot
   ```

2. Configure `mavenLocal()` and the Kotzilla repository in your consuming
   project's `settings.gradle.kts`:

   ```kotlin
   dependencyResolutionManagement {
       repositories {
           mavenLocal()
           maven { url = uri("https://repository.kotzilla.io/repository/Koin-Embedded/") }
       }
   }
   ```

3. Add the dependency in your app's `build.gradle.kts`, matching the version you
   published:

   ```kotlin
   dependencies {
       implementation("org.catrobat:aitutor:<version>")  // fixed version
       // implementation("org.catrobat:aitutor:-LOCAL")  // published with -Psnapshot
   }
   ```

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

## Theming

The tutor uses its own colors by default. To apply your app's branding
instead, wrap it in `AiTutorTheme`:

```kotlin
AiTutorTheme(colors = AiTutorColors.default().copy(primary = Color(0xFF00E5A0))) {
    AiTutorView(
        show = showTutor,
        onDismissRequest = { showTutor = false },
        codeContext = "...",
    )
}
```

The example above changes `primary` and leaves every other color at its
default. You can override any of them:

- `primary` → accent color for calls to action, links, and selection indicators.
- `onPrimary` → content color drawn on top of `primary`.
- `surface` → background of dialogs, cards, and the about screen.
- `onSurface` → primary text and icon color drawn on top of `surface`.
- `onSurfaceVariant` → secondary text and icon color used for supporting text.
- `secondaryContainer` → background of pills, chips, and menus.
- `onSecondaryContainer` → content color drawn on top of `secondaryContainer`.

## Releasing

Released builds are distributed as an `.aar` file attached to a GitHub release.

1. Bump `version` in `shared/build.gradle.kts` and merge it into `main`.
2. Open the **Actions** tab, select **Release AAR**, and run the workflow.
3. The workflow verifies the build, then attaches `aitutor-<version>.aar` to a new release tagged `v<version>`.

### Using the release in your app

1. Copy `aitutor-<version>.aar` into your app module's `libs/` folder.
2. Add the `.aar` in your app's `build.gradle.kts`:

   ```kotlin
   dependencies {
       implementation(files("libs/aitutor-<version>.aar"))
   }
   ```

   An `.aar` has no POM, so you also need to declare the dependencies this
   library uses. See `shared/build.gradle.kts` for the current list.
3. Sync Gradle and rebuild your app.

## Dependencies

This SDK uses Koin internally (embedded version) for dependency injection.
The embedded version is isolated and will not conflict with Koin usage
in your application.

- **Internal dependency:** `embedded-koin-core:3.5.6`
- **Package namespace:** `embedded.koin.*`

## License

This project is licensed under the GNU Affero General Public License v3.0. See
the [LICENSE](LICENSE) file for details.
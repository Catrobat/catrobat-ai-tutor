# Catrobat AI Tutor

A standalone AI Tutor library for Catrobat and other Android apps.

## Features

- Detect installed AI apps on the user’s device.
- `AiTutorView` composable with tutorial, input field, and copy-paste fallback.
- Pre-styled `AiTutorFloatingActionButton` to launch the tutor.
- Dynamic UI for including optional context (e.g., compiler errors).
- Kotlin Multiplatform (Android working, iOS planned).

## Getting Started

Follow these steps to integrate the AI Tutor library into your Android project.

### 1. Add the Dependency

The library will be published to Maven Central. For now, you can include it via local publishing.
Add the dependency to your app's `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("org.catrobat:ai-tutor:<latest-version>")
}
```

Replace `<latest-version>` with the current release.

### 2. Initialize the Library

You must initialize the library once when your application starts. The best place to do this is in a custom `Application` class.

#### a. Create a custom `Application` class:

If you don't already have one, create a new Kotlin class that extends `Application` and call the `AiTutorInitializer.init()` method inside `onCreate()`.

```kotlin
// In your app's main source folder, e.g., BaseApplication.kt
import android.app.Application
import org.catrobat.aitutor.AiTutorInitializer

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize the AI Tutor library
        AiTutorInitializer.init(this)
    }
}
```

#### b. Register the custom `Application` class:

Declare your new class in the `AndroidManifest.xml` file using the `android:name` attribute in the `<application>` tag.

```xml
<application
    android:name=".BaseApplication"
    ...>
    ...
</application>
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
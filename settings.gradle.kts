enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            name = "kotzilla"
            url = uri("https://repository.kotzilla.io/repository/Koin-Embedded/")
        }
    }
}

rootProject.name = "Catrobat_AI_Tutor"
include(":shared")

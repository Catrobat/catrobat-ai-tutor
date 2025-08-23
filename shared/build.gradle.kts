import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.aboutLibraries)
    alias(libs.plugins.vanniktech.mavenPublish)
}

group = "org.catrobat"
version = "0.0.1"

kotlin {
    androidTarget {
        publishLibraryVariants("release", "debug")
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            xcf.add(this)
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            // Drawable Painter
            implementation(libs.accompanist.drawablepainter)

            // Koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // Icon Extended
            implementation(compose.materialIconsExtended)

            // ViewModel
            implementation(libs.viewmodel.compose)

            // Koin
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Datastore
            implementation(libs.androidx.datastore)
            implementation(libs.androidx.datastore.preferences)

            // Coil
            implementation(libs.coil.compose)
            implementation(libs.coil3.coil.network.okhttp)

            // About Libraries
            implementation(libs.aboutlibraries.core)
            implementation(libs.aboutlibraries.compose.core)
            implementation(libs.aboutlibraries.compose.m3)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "org.catrobat.shared.generated.resources"
    generateResClass = always
}

android {
    namespace = "org.catrobat.aitutor"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        buildConfig = true
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

//    signAllPublications()

    coordinates(
        groupId = group.toString(),
        artifactId = "aitutor",
        version = version.toString(),
    )

    pom {
        name = "Catrobat AI Tutor"
        description = "A standalone AI Tutor library for Catrobat and other Android apps."
        inceptionYear = "2025"
        url = "https://github.com/Catrobat/catrobat-ai-tutor/"
        licenses {
            license {
                name = "GNU Affero General Public License v3.0"
                url = "https://www.gnu.org/licenses/agpl-3.0.en.html"
                distribution = "https://www.gnu.org/licenses/agpl-3.0.en.html"
            }
        }
        developers {
            developer {
                id = "harissabil"
                name = "Muhammed Haris Sabil Al Karim"
                url = "https://github.com/harissabil"
            }
            developer {
                id = "spipau"
                name = " Paul Spiesberger"
                url = "https://github.com/spipau"
            }
        }
        scm {
            url = "https://github.com/Catrobat/catrobat-ai-tutor/"
            connection = "scm:git:git://github.com/Catrobat/catrobat-ai-tutor.git"
            developerConnection = "scm:git:ssh://github.com/Catrobat/catrobat-ai-tutor.git"
        }
    }
}

ktlint {
    outputToConsole.set(true)
    filter {
        exclude { element ->
            element.file.path.contains("generated")
        }
    }
}

aboutLibraries {
    export {
        outputFile = file("src/commonMain/composeResources/files/aboutlibraries.json")
        prettyPrint = true
    }
}

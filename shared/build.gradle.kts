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
    alias(libs.plugins.dev.mokkery)
}

val versionMajor by extra { 1 }
val versionMinor by extra { 0 }
val versionPatch by extra { 0 }
val sdkVersionName by extra { "$versionMajor.$versionMinor.$versionPatch" }

group = "org.catrobat"
version = if (project.hasProperty("snapshot")) "-LOCAL" else sdkVersionName

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
            implementation(libs.embedded.koin.android)

            // Coil
            implementation(libs.coil3.coil.network.okhttp)

            // App Startup
            implementation(libs.androidx.startup)
        }
        androidUnitTest.dependencies {
            implementation(libs.junit)
            implementation(libs.robolectric)
            implementation(libs.androidx.junit)
        }
        commonMain.dependencies {
            api(compose.runtime)
            api(compose.ui)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // Icon Extended
            implementation(compose.materialIconsExtended)

            // ViewModel
            implementation(libs.viewmodel.compose)

            // Koin
            api(libs.embedded.koin.core)

            // Datastore
            implementation(libs.androidx.datastore)
            implementation(libs.androidx.datastore.preferences)

            // Coil
            implementation(libs.coil.compose)

            // About Libraries
            implementation(libs.aboutlibraries.core)
            implementation(libs.aboutlibraries.compose.core)
            implementation(libs.aboutlibraries.compose.m3)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
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
        buildConfigField("String", "VERSION_NAME", "\"$sdkVersionName\"")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        buildConfig = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
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
                name = "Paul Spiesberger"
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

tasks.register("packageFatAar") {
    group = "build"
    description = "Repackages shared-release.aar with bundled runtime dependencies"
    dependsOn("bundleReleaseAar")
    notCompatibleWithConfigurationCache("repackages the aar with ant")

    val aarDir = layout.buildDirectory.dir("outputs/aar")
    val workDir = layout.buildDirectory.dir("fat-aar-work")
    outputs.file(aarDir.map { it.file("shared-release-fat.aar") })

    doLast {
        val inputAar = aarDir.get().asFile.resolve("shared-release.aar")
        require(inputAar.exists()) { "run :shared:bundleReleaseAar first" }

        // unpack the lean aar and its classes.jar
        val work = workDir.get().asFile
        work.deleteRecursively()
        val aarContents = work.resolve("aar-contents")
        val mergedClasses = work.resolve("merged-classes")
        copy {
            from(zipTree(inputAar))
            into(aarContents)
        }
        copy {
            from(zipTree(aarContents.resolve("classes.jar")))
            into(mergedClasses)
        }

        val bundlePrefixes =
            listOf(
                "io.insert-koin:embedded-koin",
                "androidx.datastore",
                "com.mikepenz:aboutlibraries",
                "org.jetbrains.kotlinx:kotlinx-collections-immutable",
            )
        val excludes =
            arrayOf(
                "META-INF/*.SF",
                "META-INF/*.DSA",
                "META-INF/*.RSA",
                "META-INF/MANIFEST.MF",
                "META-INF/LICENSE*",
                "META-INF/NOTICE*",
                "META-INF/versions/**",
                "META-INF/proguard/**",
                "META-INF/*.version",
                "META-INF/*.kotlin_module",
                "module-info.class",
                // always provided by the host, bundling these would duplicate classes at dex time
                "androidx/compose/**",
                "androidx/lifecycle/**",
                "kotlin/**",
                "kotlinx/coroutines/**",
            )

        val runtimeCp =
            configurations.findByName("releaseRuntimeClasspath")
                ?: error("no releaseRuntimeClasspath configuration")
        val proguard = StringBuilder()

        runtimeCp.resolvedConfiguration.resolvedArtifacts
            .filter { artifact ->
                val id = artifact.moduleVersion.id
                bundlePrefixes.any { "${id.group}:${id.name}".startsWith(it) }
            }
            .forEach { artifact ->
                // android libraries are an .aar wrapping a nested classes.jar, jvm libraries are a plain jar
                val jars =
                    if (artifact.file.extension == "aar") {
                        val dep = work.resolve("dep/${artifact.file.nameWithoutExtension}")
                        copy {
                            from(zipTree(artifact.file))
                            into(dep)
                        }
                        dep.resolve("proguard.txt").takeIf { it.exists() }
                            ?.let { proguard.appendLine(it.readText()) }
                        dep.walkTopDown().filter { it.name == "classes.jar" }.toList()
                    } else {
                        listOf(artifact.file)
                    }
                jars.forEach { jar ->
                    copy {
                        from(zipTree(jar)) { exclude(*excludes) }
                        into(mergedClasses)
                    }
                    // keep the consumer proguard rules a bundled library ships
                    zipTree(jar).matching { include("META-INF/proguard/**") }.files
                        .forEach { if (it.isFile) proguard.appendLine(it.readText()) }
                }
            }

        // rebuild classes.jar, carry over any harvested proguard rules, zip the fat aar
        val fatClassesJar = aarContents.resolve("classes.jar")
        fatClassesJar.delete()
        ant.invokeMethod(
            "jar",
            mapOf("destfile" to fatClassesJar.path, "basedir" to mergedClasses.path),
        )
        if (proguard.isNotBlank()) {
            aarContents.resolve("proguard.txt").appendText("\n$proguard")
        }

        val fatAar = aarDir.get().asFile.resolve("shared-release-fat.aar")
        fatAar.delete()
        ant.invokeMethod(
            "zip",
            mapOf("destfile" to fatAar.path, "basedir" to aarContents.path),
        )
        logger.lifecycle("wrote ${fatAar.name} (${fatAar.length() / 1024} KB)")
    }
}

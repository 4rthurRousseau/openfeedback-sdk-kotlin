plugins {
    `embedded-kotlin`
    `kotlin-dsl`
}

group = "build-logic"

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
    maven("https://storage.googleapis.com/gradleup/m2") {
        content { includeGroupByRegex("com\\.gradleup\\..*") }
    }
}

dependencies {
    implementation(gradleApi())
    implementation(libs.librarian)
    implementation(libs.tapmoc)
    implementation(libs.android.gradle.plugin)
    implementation(libs.jetbrains.kotlin.gradle.plugin)
    implementation(libs.jetbrains.compose.compiler.gradle.plugin)
    implementation(libs.jetbrains.compose.gradle.plugin)
    implementation(libs.jetbrains.kotlinx.binary.compatibility.validator)
    implementation(libs.ben.manes.versions)
    implementation(libs.version.catalog.update)
}

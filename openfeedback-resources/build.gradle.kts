plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)

    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

library(
    namespace = "io.openfeedback.resources",
    compose = true,
    enableAndroidResources = true,
) {
    with(it) {
        sourceSets {
            findByName("commonMain")!!.apply {
                dependencies {
                    implementation(it.compose.ui)
                    api(it.compose.components.resources)

                    api(libs.lyricist)
                }
            }
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "io.openfeedback.resources"
    generateResClass = always
}

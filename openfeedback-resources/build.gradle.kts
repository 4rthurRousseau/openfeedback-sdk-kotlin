plugins {
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.kotlin.serialization)

    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

library(
    namespace = "io.openfeedback.resources",
    compose = true,
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

        androidLibraryV2 {
            experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true

            androidResources {
                enable = true
            }
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "io.openfeedback.resources"
    generateResClass = always
}

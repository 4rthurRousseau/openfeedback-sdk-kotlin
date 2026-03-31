plugins {
    alias(libs.plugins.androidMultiplatformLibrary)
}

library(
    namespace = "io.openfeedback.ui.models",
    compose = true,
) { kotlinMultiplatformExtension ->
    kotlinMultiplatformExtension.sourceSets {
        getByName("commonMain") {
            dependencies {
                implementation(kotlinMultiplatformExtension.compose.runtime)
                api(libs.vanniktech.multiplatform.locale)
                api(libs.jetbrains.kotlinx.collections.immutable)
            }
        }
    }
}
plugins {
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.kotlin.serialization)
}

library(
    namespace = "io.openfeedback.viewmodels",
    compose = true,
) { kotlinMultiplatformExtension ->
    kotlinMultiplatformExtension.sourceSets {
        getByName("commonMain") {
            dependencies {
                implementation(projects.openfeedback)
                api(projects.openfeedbackM3)
                api(projects.openfeedbackUiModels)

                implementation(kotlinMultiplatformExtension.compose.material3)
                implementation(kotlinMultiplatformExtension.compose.runtime)
                // Not sure why this is needed 🤷
                implementation(libs.jetbrains.kotlin.stdlib)

                api(libs.androidx.lifecycle.viewmodel.compose)
                api(libs.vanniktech.multiplatform.locale)
            }
        }
        getByName("androidMain") {
            dependencies {
                implementation(kotlinMultiplatformExtension.compose.preview)
            }
        }
    }
}

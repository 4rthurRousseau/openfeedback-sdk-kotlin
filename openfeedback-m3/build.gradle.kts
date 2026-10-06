plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.serialization)
}

library(
    namespace = "io.openfeedback.m3",
    compose = true,
) { kotlinMultiplatformExtension ->
    kotlinMultiplatformExtension.sourceSets {
        findByName("commonMain")!!.apply {
            dependencies {
                api(projects.openfeedbackResources)
                api(projects.openfeedbackUiModels)

                implementation(libs.compose.material3)
                implementation(libs.compose.material.icons.extended)
            }
        }
        val androidMain by getting {
            dependencies {
                with (kotlinMultiplatformExtension) {
                    implementation(libs.compose.ui.tooling)
                    implementation(libs.compose.ui.tooling.preview)
                }
            }
        }
    }
}

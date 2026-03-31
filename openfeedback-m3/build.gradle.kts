plugins {
    alias(libs.plugins.androidMultiplatformLibrary)
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

                implementation(kotlinMultiplatformExtension.compose.material3)
                implementation(kotlinMultiplatformExtension.compose.materialIconsExtended)
            }
        }
        val androidMain by getting {
            dependencies {
                with (kotlinMultiplatformExtension) {
                    implementation(compose.uiTooling)
                    implementation(compose.preview)
                }
            }
        }
    }
}

rootProject.name = "openfeedback-sdk-kotlin"

pluginManagement {
    listOf(repositories, dependencyResolutionManagement.repositories).forEach {
        it.apply {
            mavenCentral()
            google()
            gradlePluginPortal()
            maven("https://storage.googleapis.com/gradleup/m2") {
                content { includeGroupByRegex("com\\.gradleup\\..*") }
            }
        }
    }
}

includeBuild("build-logic")

include(
    ":openfeedback",
    ":openfeedback-viewmodel",
    ":openfeedback-ui-models",
    ":openfeedback-m3",
    ":openfeedback-resources",
    ":sample-app-android",
    ":sample-app-shared",
)

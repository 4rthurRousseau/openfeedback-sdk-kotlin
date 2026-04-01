import com.android.build.api.dsl.ApplicationExtension
import com.gradleup.librarian.gradle.Librarian
import compat.patrouille.configureJavaCompatibility
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget

private fun Project.configureAndroidLibrary(namespace: String) {
    extensions.configure(KotlinMultiplatformExtension::class.java) {
        androidLibraryV2 {
            experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true

            this.namespace = namespace
            compileSdk = 36
            minSdk = 23
            configureJavaCompatibility(17)
        }
    }
}

private fun Project.configureAndroidApplication(namespace: String) {
    extensions.configure(ApplicationExtension::class.java) {
        this.namespace = namespace
        defaultConfig {
            targetSdk = 36
            compileSdk = 36
            minSdk = 23
        }
        configureJavaCompatibility(17)
    }
}

private fun Project.configureKotlin(composeMetrics: Boolean) {
    extensions.configure(KotlinMultiplatformExtension::class.java) {
        val options = buildList {
            add("-Xexpect-actual-classes")

            if (!composeMetrics) {
                return@buildList
            }

            if (project.findProperty("composeCompilerReports") == "true") {
                add("-P")
                add("plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.layout.buildDirectory.asFile.get().absolutePath}/compose_compiler")
            }

            if (project.findProperty("composeCompilerMetrics") == "true") {
                add("-P")
                add("plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${project.layout.buildDirectory.asFile.get().absolutePath}/compose_compiler")
            }
        }

        compilerOptions {
            freeCompilerArgs.addAll(options)
        }
    }
}

private fun Project.configureKMP() {
    extensions.configure(KotlinMultiplatformExtension::class.java) {
        applyDefaultHierarchyTemplate()
        targets.withType(KotlinAndroidTarget::class.java).configureEach {
            publishLibraryVariants("release")
        }
        iosX64()
        iosArm64()
        iosSimulatorArm64()
    }
}

fun Project.library(
    namespace: String,
    compose: Boolean = false,
    kotlin: (KotlinMultiplatformExtension) -> Unit
) {
    val kotlinMultiplatformExtension = applyKotlinMultiplatformPlugin()
    if (compose) {
        applyJetbrainsComposePlugin()
    }
    configureAndroidLibrary(namespace = namespace)
    configureKMP()

    configureKotlin(compose)

    kotlin(kotlinMultiplatformExtension)

    Librarian.module(project)
}

fun Project.androidApp(
    namespace: String,
) {
    configureAndroidApplication(namespace = namespace)
}

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryExtension
import com.gradleup.librarian.gradle.Librarian
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import tapmoc.configureJavaCompatibility
import tapmoc.configureKotlinCompatibility

private fun Project.configureAndroidLibrary(namespace: String, enableAndroidResources: Boolean) {
    extensions.configure<KotlinMultiplatformExtension> {
        val ext = (this as ExtensionAware).extensions
            .getByName("androidLibrary") as KotlinMultiplatformAndroidLibraryExtension

        ext.namespace = namespace
        ext.compileSdk = 36
        ext.minSdk = 23
        ext.androidResources.enable = enableAndroidResources
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
    }


}

private fun Project.configureKotlin(composeMetrics: Boolean) {
    tasks.withType(KotlinCompilationTask::class.java) {
        val freeCompilerArgs = compilerOptions.freeCompilerArgs
        freeCompilerArgs.add("-Xexpect-actual-classes")
        if (composeMetrics) {
            if (project.findProperty("composeCompilerReports") == "true") {
                freeCompilerArgs.add("-P")
                freeCompilerArgs.add("plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.layout.buildDirectory.asFile.get().absolutePath}/compose_compiler")
            }
            if (project.findProperty("composeCompilerMetrics") == "true") {
                freeCompilerArgs.add("-P")
                freeCompilerArgs.add("plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${project.layout.buildDirectory.asFile.get().absolutePath}/compose_compiler")
            }
        }
    }
}

private fun Project.configureKMP() {
    extensions.configure<KotlinMultiplatformExtension> {
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
    enableAndroidResources: Boolean = false,
    kotlin: (KotlinMultiplatformExtension) -> Unit
) {
    val kotlinMultiplatformExtension = applyKotlinMultiplatformPlugin()
    if (compose) {
        applyJetbrainsComposePlugin()
    }
    configureAndroidLibrary(namespace = namespace, enableAndroidResources = enableAndroidResources)
    configureKMP()

    configureKotlin(composeMetrics = compose)

    kotlin(kotlinMultiplatformExtension)

    Librarian.module(project)
}

fun Project.androidApp(
    namespace: String,
) {
    configureJavaCompatibility(17)
    configureKotlinCompatibility("2.0.0")
    configureAndroidApplication(namespace = namespace)
    configureKotlin(composeMetrics = true)
}

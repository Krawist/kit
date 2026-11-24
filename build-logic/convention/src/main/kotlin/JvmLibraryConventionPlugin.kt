import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType

/** Pure Kotlin module, without Android: logic testable with fast JVM tests. */
class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.jvm")
            extensions.configure<JavaPluginExtension> {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
            configureKotlinJvmTarget()
            configureUnitTestDependencies()
            tasks.withType<Test>().configureEach { useJUnitPlatform() }
            // CI runs testDebugUnitTest on every module: alias for JVM modules.
            tasks.register("testDebugUnitTest") {
                group = "verification"
                dependsOn("test")
            }
        }
    }
}

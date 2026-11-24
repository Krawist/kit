plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt)
}

// The apps include this build to use its modules: they find them by this group (`io.github.krawist:detekt-rules`).
allprojects {
    group = "io.github.krawist"
}

// ktlint applies to each module; detekt is configured once for the whole repository.
subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    // The ktlint plugin only finds the sources of the Kotlin plugins, not those of AGP 9's built-in Kotlin: add them.
    plugins.withId("com.android.base") {
        configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
            kotlinScriptAdditionalPaths { include(fileTree("src") { include("**/*.kt") }) }
        }
    }
}

detekt {
    buildUponDefaultConfig = true
    config.setFrom(files("config/detekt/detekt.yml"))
    source.setFrom(
        files(
            subprojects.flatMap { listOf("${it.projectDir}/src/main/kotlin", "${it.projectDir}/src/test/kotlin") },
        ),
    )
    parallel = true
}

dependencies {
    detektPlugins(projects.detektRules)
}

// detekt 1.23.x ships its own Kotlin 2.0.21 compiler: keep Gradle from aligning it, or the rules it runs, with the
// project's version.
configurations.matching { it.name == "detekt" || it.name == "detektPlugins" }.configureEach {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin") {
            useVersion("2.0.21")
        }
    }
}

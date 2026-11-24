plugins {
    alias(libs.plugins.krawist.android.library)
    alias(libs.plugins.krawist.android.compose)
}

android {
    namespace = "io.github.krawist.design"
}

dependencies {
    implementation(libs.haze)
    implementation(libs.haze.blur)
}

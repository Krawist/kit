# Kit

Shared build and code of my Android apps (Cauri, Eifi, Griot). Group: `io.github.krawist`.

## Content

```
build-logic/              Gradle convention plugins (io.github.krawist.android.application…)
gradle/libs.versions.toml Version catalog of all the apps
detekt-rules/             Shared detekt rules (HardcodedUiText), rule set `krawist`
```

## Use in an app

The app is checked out next to this repository (`../Kit`). In its `settings.gradle.kts`:

```kotlin
pluginManagement {
    includeBuild("../Kit/build-logic")
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../Kit/gradle/libs.versions.toml"))
        }
    }
}

// Modules of the kit, such as detekt-rules.
includeBuild("../Kit")
```

The app has no `gradle/libs.versions.toml` of its own: a new library goes in the catalog of the kit.

## Convention plugins

- `io.github.krawist.android.application`, `io.github.krawist.android.library`: Android module, JUnit 5 and Kotest.
- `io.github.krawist.android.compose`: Compose on an Android module.
- `io.github.krawist.android.feature`: screen module (library, Compose, Hilt, navigation and the app's
  `:core:design`).
- `io.github.krawist.android.hilt`, `io.github.krawist.android.room`: Hilt and Room with KSP.
- `io.github.krawist.jvm.library`: Kotlin module without Android.

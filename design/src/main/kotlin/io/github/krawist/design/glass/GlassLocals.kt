package io.github.krawist.design.glass

import androidx.compose.runtime.staticCompositionLocalOf
import dev.chrisbanes.haze.HazeState

/** Colours of the glass surfaces, provided by [GlassTheme]. */
internal val LocalGlassColors = staticCompositionLocalOf<GlassColors> {
    error("No glass colours: call GlassTheme in the theme of the app.")
}

/** Whether glass surfaces blur, chosen once at startup and provided by [GlassTheme]. */
internal val LocalGlassBlur = staticCompositionLocalOf { false }

/** What glass surfaces blur, provided by [GlassScreen]; `null` outside of one. */
internal val LocalGlassHazeState = staticCompositionLocalOf<HazeState?> { null }

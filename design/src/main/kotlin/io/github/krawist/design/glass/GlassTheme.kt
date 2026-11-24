package io.github.krawist.design.glass

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/**
 * Gives the glass surfaces of [content] their colours, to be called by the theme of the app.
 *
 * @param colors Colours of the current mode (light or dark), from the palette of the app.
 * @param blur Whether glass surfaces blur what is behind them, from [supportsGlassBlur] at startup; otherwise they are
 * opaque tinted surfaces.
 */
@Composable
fun GlassTheme(colors: GlassColors, blur: Boolean, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalGlassColors provides colors,
        LocalGlassBlur provides blur,
        content = content,
    )
}

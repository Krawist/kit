package io.github.krawist.design.glass

import androidx.compose.ui.graphics.Color

/**
 * Colours of the glass surfaces, taken from the palette of the app and provided by [GlassTheme].
 *
 * @property background Colour of the screen behind the glass: fill of a [GlassScreen], and what the glass is drawn over
 * when it does not blur.
 * @property fill Translucent fill of glass surfaces (cards, list rows, chips).
 * @property strongFill More opaque fill of the floating bars (bottom bar, toolbar): `GlassSurface(strong = true)`.
 * @property border Hairline border drawn around every glass surface.
 * @property shadow Colour of the soft shadow under a glass surface that floats above the content.
 */
data class GlassColors(
    val background: Color,
    val fill: Color,
    val strongFill: Color,
    val border: Color,
    val shadow: Color,
)

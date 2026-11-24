package io.github.krawist.design.component

import androidx.compose.ui.graphics.Color

/**
 * One blurred disc of a [backdrop], drawn as a radial gradient fading to transparent.
 *
 * @property color Colour at the centre, with its opacity.
 * @property x Horizontal position of the centre, as a fraction of the width (0 = left edge, 1 = right edge).
 * @property y Vertical position of the centre, as a fraction of the height (0 = top edge, 1 = bottom edge).
 * @property radius Radius, as a fraction of the width.
 */
data class BackdropShape(val color: Color, val x: Float, val y: Float, val radius: Float)

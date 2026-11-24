package io.github.krawist.design.component

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

/**
 * Draws the blurred [shapes] behind the content. They are radial gradients, so they look the same on every phone,
 * with or without blur support.
 */
fun Modifier.backdrop(shapes: List<BackdropShape>): Modifier = drawBehind {
    shapes.forEach { shape ->
        val center = Offset(size.width * shape.x, size.height * shape.y)
        val radius = size.width * shape.radius
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(shape.color, shape.color.copy(alpha = 0f)),
                center = center,
                radius = radius,
            ),
            radius = radius,
            center = center,
        )
    }
}

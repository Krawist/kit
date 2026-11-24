package io.github.krawist.design.glass

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource

// A glass surface blurs the layers below the nearest one that contains it.
private const val BACKGROUND_LAYER = 0f
private const val CONTENT_LAYER = 1f

/**
 * Screen that holds what its glass surfaces blur, in three layers:
 * - [background]: the blurred shapes behind everything;
 * - [content]: the screen content, often a scrolling list; its glass cards blur the [background] only;
 * - [overlay]: what floats above the content (bottom bar, sheets); its glass blurs the [background] and the [content].
 *
 * It fills the room it is given; with [fillMaxSize] `false`, it is as large as its [content], such as a sheet as tall
 * as what it holds, and the [background] covers that size.
 */
@Composable
fun GlassScreen(
    modifier: Modifier = Modifier,
    fillMaxSize: Boolean = true,
    background: @Composable BoxScope.() -> Unit = {},
    overlay: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    val hazeState = remember { HazeState() }
    CompositionLocalProvider(LocalGlassHazeState provides hazeState) {
        val fill = if (fillMaxSize) Modifier.fillMaxSize() else Modifier
        Box(modifier = modifier.then(fill).background(LocalGlassColors.current.background)) {
            Box(
                modifier = Modifier.matchParentSize().hazeSource(state = hazeState, zIndex = BACKGROUND_LAYER),
                content = background,
            )
            Box(
                modifier = fill.hazeSource(state = hazeState, zIndex = CONTENT_LAYER),
                content = content,
            )
            overlay()
        }
    }
}

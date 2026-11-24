package io.github.krawist.design.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

/**
 * Shows [content] for [value] while it is set, coming in with [enter]; once [value] is `null`, the last value leaves
 * with [exit] instead of vanishing, as an undo bar that goes back down.
 */
@Composable
fun <T : Any> AnimatedPresence(
    value: T?,
    enter: EnterTransition,
    exit: ExitTransition,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit,
) {
    // Not a state: it only keeps what the leaving content shows, never asks for a new composition.
    val last = remember { arrayOfNulls<Any>(1) }
    if (value != null) last[0] = value
    AnimatedVisibility(visible = value != null, modifier = modifier, enter = enter, exit = exit) {
        @Suppress("UNCHECKED_CAST") // Only ever set from [value].
        (last[0] as? T)?.let { content(it) }
    }
}

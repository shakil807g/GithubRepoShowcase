package com.shakil.githubreposhowcase.ui.trending_repo

import androidx.compose.animation.*
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.fastForEach

@ExperimentalAnimationApi
@Composable
fun <S> AnimatedContent(
    targetState: S,
    modifier: Modifier = Modifier,
    transitionSpec: Transition.Segment<S>.() -> EnterExitTransition,
    content: @Composable() Transition<EnterExitState>.(targetState: S) -> Unit
) {
    val transition = updateTransition(targetState)
    transition.AnimatedContent(modifier, transitionSpec, content)
}

@ExperimentalAnimationApi
@Composable
fun <S> Transition<S>.AnimatedContent(
    modifier: Modifier = Modifier,
    transitionSpec: Transition.Segment<S>.() -> EnterExitTransition,
    content: @Composable() Transition<EnterExitState>.(targetState: S) -> Unit
) {
    val screenTransition = this
    val screens = remember { mutableStateListOf<TransitionScreen<S>>() }
    val lastTarget = remember { mutableStateOf(screenTransition.targetState) }

    if (lastTarget.value != screenTransition.targetState || screens.isEmpty()) {
        lastTarget.value = screenTransition.targetState
        // Only manipulate the list when the state is changed, or in the first run.
        val keys = screens.map { it.key }.run {
            if (!contains(screenTransition.targetState)) {
                toMutableList().also { it.add(screenTransition.targetState) }
            } else {
                this
            }
        }
        val enterExit = transitionSpec(screenTransition.segment)
        val newScreens = keys.map { key ->
            val finishedExiting = mutableStateOf(false)
            TransitionScreen(key, finishedExiting) {
                val enter = remember { mutableStateOf(enterExit.enter) }
                if (screenTransition.segment.targetState == key) {
                    enter.value = enterExit.enter
                }
                val exit = remember { mutableStateOf(fadeOut()) }
                if (screenTransition.segment.initialState == key) {
                    exit.value = enterExit.exit
                }

                screenTransition.AnimatedVisibility(
                    visible = { it == key },
                    // TODO: Changing this amid animation may be a problem
                    enter = enter.value,
                    exit = exit.value,
                ) {
                    transition.content(key)
                }
            }
        }
        screens.clear()
        screens.addAll(newScreens)
    } else if (screenTransition.currentState == screenTransition.targetState) {
        // Remove all the intermediate items from the list once the animation is finished.
        screens.removeAll { it.key != screenTransition.targetState }
    }

    Box(modifier) {
        screens.fastForEach {
            key(it.key) {
                it.content(this@Box)
            }
        }
    }
}

private data class TransitionScreen<T>(
    val key: T,
    val finishedExiting: State<Boolean>, // not used
    val content: @Composable() BoxScope.() -> Unit,
)

// TODO: Some defaults: fadeThrough, horizontal slide, etc
@ExperimentalAnimationApi
class EnterExitTransition(
    val enter: EnterTransition,
    val exit: ExitTransition
    // val sequential?
)
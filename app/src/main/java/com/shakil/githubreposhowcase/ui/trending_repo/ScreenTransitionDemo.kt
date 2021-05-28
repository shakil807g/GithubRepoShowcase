package com.shakil.githubreposhowcase.ui.trending_repo

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ScreenTransitionDemo() {
    Column {
        Spacer(Modifier.size(40.dp))
        var targetScreen by remember { mutableStateOf(TestScreens.Screen1) }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {
                targetScreen = when ((targetScreen.ordinal + 2) % 3) {
                    1 -> TestScreens.Screen2
                    2 -> TestScreens.Screen3
                    else -> TestScreens.Screen1
                }
            }, modifier = Modifier.align(Alignment.CenterVertically).padding(10.dp)) {
                Text("Previous screen")
            }
            Button(onClick = {
                targetScreen = when (targetScreen.ordinal + 1) {
                    1 -> TestScreens.Screen2
                    2 -> TestScreens.Screen3
                    else -> TestScreens.Screen1
                }
            }, modifier = Modifier.align(Alignment.CenterVertically).padding(10.dp)) {
                Text("Next screen")
            }
        }
        val transition = updateTransition(targetScreen)
        transition.AnimatedContent(
            transitionSpec = {
                when {
                    TestScreens.Screen1 isTransitioningTo TestScreens.Screen2 ||
                            TestScreens.Screen2 isTransitioningTo TestScreens.Screen1 -> {
                        EnterExitTransition(
                            expandHorizontally(animationSpec = tween(10000)) + fadeIn(),
                            shrinkVertically(animationSpec = tween(10000))
                                    + fadeOut(animationSpec = tween(10000))
                        )
                    }
                    TestScreens.Screen2 isTransitioningTo TestScreens.Screen3 -> {
                        EnterExitTransition(
                            slideInHorizontally(
                                initialOffsetX = { -it }, animationSpec = tween
                                    (500)
                            ),
                            slideOutHorizontally(targetOffsetX = { it }, tween(500))
                        )
                    }
                    // TODO: Make the symmetric enter/exit more concise
                    TestScreens.Screen3 isTransitioningTo TestScreens.Screen2 -> {
                        EnterExitTransition(
                            slideInHorizontally(
                                initialOffsetX = { it }, animationSpec = tween
                                    (500)
                            ),
                            slideOutHorizontally(targetOffsetX = { -it }, tween(500))
                        )
                    }
                    else -> {
                        // Fade through
                        EnterExitTransition(
                            fadeIn(animationSpec = tween(300, 300)),
                            fadeOut(animationSpec = tween(durationMillis = 300))
                        )
                    }
                }
            }
        ) {
            when (it) {
                TestScreens.Screen1 -> Screen1()
                TestScreens.Screen2 -> Screen2()
                TestScreens.Screen3 -> Screen3()
            }
        }
    }
}

enum class TestScreens {
    Screen1,
    Screen2,
    Screen3
}

@Composable
fun Screen1() {
    Box(modifier = Modifier.fillMaxSize().padding(30.dp).background(Color.Red)) {
        Text("Screen 1", modifier = Modifier.align(Center))
    }
}

@Composable
fun Screen2() {
    Box(modifier = Modifier.fillMaxSize().padding(30.dp).background(Color.Green)) {
        Text("Screen 2", modifier = Modifier.align(Center))
    }
}

@Composable
fun Screen3() {
    Box(modifier = Modifier.fillMaxSize().padding(30.dp).background(Color.Blue)) {
        Text("Screen 3", modifier = Modifier.align(Center))
    }
}
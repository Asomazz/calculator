package com.example.counterapp

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.counterapp.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CounterAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TrueOrFalse(modifier = Modifier, innerPadding = innerPadding)
                }
            }
        }
    }
}

@Composable
fun TrueOrFalse(modifier: Modifier, innerPadding: PaddingValues) {
    val context = LocalContext.current
    val correctSound = remember { MediaPlayer.create(context, R.raw.correct_sound) }
    val wrongSound = remember { MediaPlayer.create(context, R.raw.wrong_sound) }
    val completeSound = remember { MediaPlayer.create(context, R.raw.complete_sound) }

    val questions = listOf(
        "Android is an operating system." to true,
        "Kotlin is supported for iOS development." to false,
        "Kotlin is supported for Android development." to true
    )

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var isAnswerCorrect by remember { mutableStateOf<Boolean?>(null) }
    var score by remember { mutableStateOf(0) }
    var isGameOver by remember { mutableStateOf(false) }

    val currentQuestion = if (currentQuestionIndex < questions.size) questions[currentQuestionIndex] else null

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Black, Color(0xFF1C1C1E))
                )
            )
            .padding(innerPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "QUIZ OF WISDOM",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.White
            )

            if (isGameOver) {
                LaunchedEffect(Unit) { completeSound.start() }

                Text(
                    text = "YOUR SCORE: $score / ${questions.size}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
                Button(
                    onClick = {
                        currentQuestionIndex = 0
                        isAnswerCorrect = null
                        score = 0
                        isGameOver = false
                    },
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("RESTART", color = Color.Black)
                }
            } else {
                Text(
                    text = currentQuestion?.first ?: "",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )

                AnimatedVisibility(
                    visible = isAnswerCorrect != null,
                    enter = scaleIn(tween(400)) + fadeIn(tween(300))
                ) {
                    Text(
                        text = if (isAnswerCorrect == true) "CORRECT ✅" else "WRONG ❌",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isAnswerCorrect == true) Color(0xFFB6F399) else Color(0xFFFF6B6B)
                    )
                }

                if (isAnswerCorrect == true) {
                    Button(
                        onClick = {
                            if (currentQuestionIndex < questions.size - 1) {
                                currentQuestionIndex++
                                isAnswerCorrect = null
                            } else {
                                isGameOver = true
                            }
                        },
                        shape = RoundedCornerShape(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text("NEXT", color = Color.Black)
                    }
                }

                if (isAnswerCorrect != true) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = {
                                val isCorrect = currentQuestion?.second == true
                                isAnswerCorrect = isCorrect
                                if (isCorrect) {
                                    score++
                                    correctSound.start()
                                } else {
                                    wrongSound.start()
                                }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        ) {
                            Text("TRUE", color = Color.Black)
                        }

                        Button(
                            onClick = {
                                val isCorrect = currentQuestion?.second == false
                                isAnswerCorrect = isCorrect
                                if (isCorrect) {
                                    score++
                                    correctSound.start()
                                } else {
                                    wrongSound.start()
                                }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        ) {
                            Text("FALSE", color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}
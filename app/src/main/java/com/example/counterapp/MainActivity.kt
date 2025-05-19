package com.example.counterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.counterapp.ui.theme.CounterAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            CounterAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()){innerPadding ->
                    TrueOrFalse(modifier = Modifier, innerPadding = innerPadding)
                }

           }
        }
    }
}

@Composable
fun TrueOrFalse(modifier: Modifier, innerPadding: PaddingValues) {
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isGameOver) {

            Text(
                text = "Game Over! Your Score: $score / ${questions.size}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Button(onClick = {
                currentQuestionIndex = 0
                isAnswerCorrect = null
                score = 0
                isGameOver = false
            }) {
                Text("Restart Game")
            }
        }
            else {
            Text(
                text = currentQuestion?.first ?: "",
                fontSize = 30.sp,
                fontWeight = FontWeight.Normal
            )
            when (isAnswerCorrect) {
                true -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = modifier
                            .clip(CircleShape)
                            .size(170.dp)
                            .background(color = Color.Green)
                    )
                    {
                        Text("Correct Answer")
                    }
                    Button(onClick = {
                        if (currentQuestionIndex < questions.size - 1) {
                            currentQuestionIndex++
                            isAnswerCorrect = null
                        } else {
                            isGameOver = true
                        }
                    }) {
                        Text("Next Question")
                    }
                }
                false -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(170.dp)
                            .background(color = Color.Red)
                    ) {
                        Text("Wrong Answer")
                    }
                }
                null -> {

                }
            }

            if (isAnswerCorrect != true) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {

                    Button(
                        onClick = { val isCorrect = currentQuestion?.second == true
                            isAnswerCorrect = isCorrect
                            if (isCorrect) score++},
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                    ) {
                        Text("True")
                    }
                    Button(
                        onClick = {  val isCorrect = currentQuestion?.second == false
                            isAnswerCorrect = isCorrect
                            if (isCorrect) score++},
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                    ) {
                        Text("False")
                    }
                }

            }

        }

        }

    }

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    CounterAppTheme {
//        Greeting("Android")
//    }
//}
package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme

data class CalculatorButton(val symbol: String)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorTheme {
                CalculatorScreen()
                }
            }
        }
    }


@Composable
fun CalculatorScreen() {
    var expression by remember { mutableStateOf("") }
    val buttons = listOf(
        listOf(CalculatorButton("C"), CalculatorButton("⌫"), CalculatorButton("("), CalculatorButton(")")),
        listOf(CalculatorButton("7"), CalculatorButton("8"), CalculatorButton("9"), CalculatorButton("/")),
        listOf(CalculatorButton("4"), CalculatorButton("5"), CalculatorButton("6"), CalculatorButton("*")),
        listOf(CalculatorButton("1"), CalculatorButton("2"), CalculatorButton("3"), CalculatorButton("-")),
        listOf(CalculatorButton("0"), CalculatorButton("."), CalculatorButton("+"), CalculatorButton("="))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
            ) {
            val scrollState = rememberScrollState()

            LaunchedEffect(expression) {
                scrollState.animateScrollTo(scrollState.maxValue)
            }

            Text(
                text = expression,
                fontSize = 48.sp,
                maxLines = 1,
                modifier = Modifier
                    .horizontalScroll(scrollState)
            )
        }

        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            buttons.forEach { row ->
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { button ->
                        Button(
                            onClick = {
                                when(button.symbol) {
                                    "C" -> expression = ""
                                    "⌫" -> if(expression.isNotEmpty()) expression = expression.dropLast(1)
                                    "=" -> { }
                                    else -> expression += button.symbol
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = button.symbol,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
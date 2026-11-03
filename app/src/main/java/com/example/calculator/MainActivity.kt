package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import com.example.calculator.ui.theme.AppTheme


enum class ButtonRole { NUMBER, OPERATION, EQUALS }

data class CalculatorButton(
    val symbol: String,
    val role: ButtonRole = ButtonRole.NUMBER
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                CalculatorScreen()
                }
            }
        }
    }


@Composable
fun CalculatorScreen() {
    var expression by remember { mutableStateOf("") }
    val basicButtons = listOf(
        listOf(CalculatorButton("C", ButtonRole.OPERATION), CalculatorButton("⌫", ButtonRole.OPERATION), CalculatorButton("(", ButtonRole.OPERATION), CalculatorButton(")", ButtonRole.OPERATION)),
        listOf(CalculatorButton("7"), CalculatorButton("8"), CalculatorButton("9"), CalculatorButton("/", ButtonRole.OPERATION)),
        listOf(CalculatorButton("4"), CalculatorButton("5"), CalculatorButton("6"), CalculatorButton("*", ButtonRole.OPERATION)),
        listOf(CalculatorButton("1"), CalculatorButton("2"), CalculatorButton("3"), CalculatorButton("-", ButtonRole.OPERATION)),
        listOf(CalculatorButton("0"), CalculatorButton("."), CalculatorButton("+", ButtonRole.OPERATION), CalculatorButton("=", ButtonRole.EQUALS))
    )

    val scientificButtons = listOf(
        listOf(CalculatorButton("sin", ButtonRole.OPERATION), CalculatorButton("cos", ButtonRole.OPERATION), CalculatorButton("tan", ButtonRole.OPERATION), CalculatorButton("π", ButtonRole.OPERATION)),
        listOf(CalculatorButton("√", ButtonRole.OPERATION), CalculatorButton("x^y", ButtonRole.OPERATION), CalculatorButton("ln", ButtonRole.OPERATION), CalculatorButton("e", ButtonRole.OPERATION))
    )

    val configuration = androidx.compose.ui.platform.LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    val onButtonClick = { button: CalculatorButton ->
        when (button.symbol) {
            "C" -> expression = ""
            "⌫" -> if (expression.isNotEmpty()) expression = expression.dropLast(1)
            "=" -> { }
            "sin", "cos", "tan", "ln", "√" -> expression += "${button.symbol}("
            "x^y" -> expression += "^"
            else -> expression += button.symbol
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .horizontalScroll(scrollState)
            )
        }

        if (isLandscape) {
            Row(
                modifier = Modifier.weight(2f).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ButtonGrid(buttons = scientificButtons, modifier = Modifier.weight(1f), onClick = onButtonClick)
                ButtonGrid(buttons = basicButtons, modifier = Modifier.weight(1f), onClick = onButtonClick)
            }
        } else {
                ButtonGrid(buttons = basicButtons, modifier = Modifier.weight(2f), onClick = onButtonClick)
        }
    }
}

@Composable
fun ButtonGrid(
    buttons: List<List<CalculatorButton>>,
    modifier: Modifier = Modifier,
    onClick: (CalculatorButton) -> Unit
) {
    Column(
        modifier = modifier,
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
                    val containerColor = when (button.role) {
                        ButtonRole.NUMBER -> MaterialTheme.colorScheme.surfaceVariant
                        ButtonRole.OPERATION -> MaterialTheme.colorScheme.secondaryContainer
                        ButtonRole.EQUALS -> MaterialTheme.colorScheme.primary
                    }
                    val contentColor = when (button.role) {
                        ButtonRole.NUMBER -> MaterialTheme.colorScheme.onSurfaceVariant
                        ButtonRole.OPERATION -> MaterialTheme.colorScheme.onSecondaryContainer
                        ButtonRole.EQUALS -> MaterialTheme.colorScheme.onPrimary
                    }

                    Button(
                        onClick = { onClick(button) },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = containerColor,
                            contentColor = contentColor
                        )
                    ) {
                        Text(
                            text = button.symbol,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
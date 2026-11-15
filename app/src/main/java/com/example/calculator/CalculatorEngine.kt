package com.example.calculator

import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.License

fun evaluateExpression(rawExpression: String): String? {
    License.iConfirmNonCommercialUse("Weronika")

    val cleaned = rawExpression
        .trim()
        .replace(',', '.')
        .replace("√", "sqrt")
    if (cleaned.isEmpty()) return null
    if (cleaned.last() in listOf('+', '-', '*', '/', '^', '.')) return null

    val exp = Expression(cleaned)
    if (!exp.checkSyntax()) return null

    val result = exp.calculate()
    if (result.isNaN() || result.isInfinite()) {
        return if(cleaned.contains("/0")) {
            "Błąd: dzielenie przez zero"
        } else {
            "Błąd: nieprawidłowa dziedzina"
        }
    }

    return if(result % 1.0 == 0.0 && result in -1e15..1e15) {
        result.toLong().toString()
    } else {
        result.toString()
    }
}
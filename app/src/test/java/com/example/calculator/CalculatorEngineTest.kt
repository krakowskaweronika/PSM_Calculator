package com.example.calculator

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CalculatorEngineTest {

    @Test
    fun orderOfOperations_returns6() {
        assertEquals("6", evaluateExpression("2+2*2"))
    }

    @Test
    fun parentheses_returns8() {
        assertEquals("8", evaluateExpression("(2+2)*2"))
    }

    @Test
    fun divisionByZero_returnsReadableError() {
        assertEquals("Błąd: dzielenie przez zero", evaluateExpression("1/0"))
    }

    @Test
    fun incompleteExpression_returnsNull() {
        assertNull(evaluateExpression("2+"))
    }

    @Test
    fun unclosedParenthesis_returnsNull() {
        assertNull(evaluateExpression("((2+2)"))
    }

    @Test
    fun emptyInput_returnsNull() {
        assertNull(evaluateExpression(""))
    }

    @Test
    fun negativeSquareRoot_returnsDomainError() {
        assertEquals("Błąd: nieprawidłowa dziedzina", evaluateExpression("sqrt(-1)"))
    }

    @Test
    fun largeNumbers_returnsResult() {
        assertEquals("1.524157875019052E18", evaluateExpression("1234567890*1234567890"))
    }

    @Test
    fun decimalSeparator_dotAndCommaGiveSameResult() {
        val withDot = evaluateExpression("2.5+1")
        val withComma = evaluateExpression("2,5+1")
        assertEquals("3.5", withDot)
        assertEquals(withDot, withComma)
    }
}
package com.cursosandroidant.calculator

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

//V-54,paso 1.0
@RunWith(MockitoJUnitRunner::class)
class CalculatorUtilsMockTest{
    @Mock
    lateinit var operations: Operations
    @Mock
    lateinit var listener: OnResolveListener

    //Nuestra clase principal
    lateinit var calculatorUtils: CalculatorUtils

    @Before
    fun setup(){
        //recibe las operaciones y el listener
        calculatorUtils = CalculatorUtils(operations, listener)
    }

    //V-55,Paso 1.1
    @Test
    //LLamamos a la funcion de checkOrResolve
    fun cacl_callCheckOrResolve_noReturn(){
        val operation = "-5x2.5"
        val isFromResolve = true
        calculatorUtils.checkOrResolve(operation, isFromResolve)
        verify(operations).tryResolve(operation, isFromResolve, listener)
    }

    //V-56, paso 1.2
    @Test
    fun calc_callAddOperator_validSub_noReturn(){
        val operator = "-"
        val operation = "4+" //4+-3
        var isCorrect = false
        calculatorUtils.addOperator(operator, operation){
            isCorrect = true
        }
        assertTrue(isCorrect)
    }

    //Tarea , paso 1.3
    @Test
    fun calc_callAddOperator_invalidSub_noReturn() {
        val operator = "-"
        //Ya no se ejecuta con el.
        val operation = "4+." //4+-3
        var isCorrect = false
        calculatorUtils.addOperator(operator, operation){
            isCorrect = true
        }
        assertFalse(isCorrect)
    }

    //V-57,Paso 1.4
    @Test
    fun calc_callAddPoint_firstPoint_noReturns(){
        //aqui podemos añadir un punto despues de 3x2.
        val operation = "3x2"
        var isCorrect = false
        calculatorUtils.addPoint(operation){
            isCorrect = true
        }
        assertTrue(isCorrect)
        verifyNoInteractions(operations)
    }

    //V-58,Paso 1.5
    @Test
    fun calc_callAddPoint_secondPoint_noReturns(){
        val operation = "3.5x2"
        val operator = "x"
        var isCorrect = false

        `when`(operations.getOperator(operation)).thenReturn("x")
        `when`(operations.divideOperation(operator, operation)).thenReturn(arrayOf("3.5", "2"))

        calculatorUtils.addPoint(operation){
            isCorrect = true
        }
        assertTrue(isCorrect)
        verify(operations).getOperator(operation)
        verify(operations).divideOperation(operator, operation)
    }
}
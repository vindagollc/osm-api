package com.example.osmapi

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class GreetingTest {

    @Test
    fun shouldGreet(){
        val greeting = Greeting()
        val result = greeting.showSomething()
        assertEquals(result,"Hello World")
    }

    @Test
    fun testAPIResult(){
        val greeting = Greeting()
        runBlocking {
            val result = greeting.greeting()
            print(result)
            assertNotEquals(result,"")
        }


    }
}
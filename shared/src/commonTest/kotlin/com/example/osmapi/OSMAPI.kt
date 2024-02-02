package com.example.osmapi

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class OSMAPITests {
    private  val apiInstance = OSMAPI()

    @Test
    fun testCapabilities() {
        runBlocking {
            val result = apiInstance.getCapabilities()
            print(result)
            assertEquals(result,"0.6")
        }
    }
    @Test
    fun testLocalCapabilities(){
        val result = apiInstance.getLocalCapabilities()
        assertEquals(result,"0.6")
    }
}


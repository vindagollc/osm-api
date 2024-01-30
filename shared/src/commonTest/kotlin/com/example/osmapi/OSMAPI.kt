package com.example.osmapi

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertNotEquals

class OSMAPITests {
    private  val apiInstance = OSMAPI()

    @Test
    fun testCapabilities() {
        runBlocking {
            val result = apiInstance.getCapabilities()
            print(result)
            assertNotEquals(result,"")
        }
    }
}
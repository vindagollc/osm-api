package com.example.osmapi

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

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
    @Test
    fun testLocalUser() {
        val result = apiInstance.getLocalUser()
        assertEquals(result,"0.6")
    }

    @Test
    fun testRemoteUser(){
        runBlocking {
            val result = apiInstance.getUser()
            print(result)
            assertEquals(result.id, 1)
        }
    }

    @Test
    fun testGetChangesets() {
        runBlocking {
            val result = apiInstance.getUserChangesets()
            print(result)
            assertTrue { result.isNotEmpty() }
            val first = result.first()
            assertEquals(first.uid,"1") // First user
        }
    }
    @Test
    fun testLocalChangesets(){
        val result = apiInstance.getLocalChangesets()
        assertTrue(result.isNotEmpty())
        val firstChangeset = result.first()
        print(firstChangeset.id)
        assertEquals(firstChangeset.id,"73")
    }

    @Test
    fun testOpenChangeset() {
        runBlocking {
            val changesetR = apiInstance.openChangeset()
            print("----")
            print(changesetR)
            print("-----")
            assertTrue(changesetR.isNotEmpty())
        }
    }

    @Test
    fun testCloseChangeset() {
        runBlocking {
            val closedCR = apiInstance.closeChangeset("75")
            print("--> Closed <---\n")
            print(closedCR)
            print("\n<---CR--->")
        }
    }

    @Test
    fun testGetNode(){
        runBlocking {
            val node = apiInstance.getSingleNode("75")
            assertTrue { node.isNotEmpty() }
        }
    }
}


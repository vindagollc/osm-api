package com.example.osmapi.user

import com.example.osmapi.core.OSMConnection
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class UserApiTest {
    private val posmBase = "https://waylyticsposm.westus2.cloudapp.azure.com/api/0.6/"

    @Test
    fun testUserApi() {
        val osmConnection = OSMConnection(posmBase, "OSMAPI")
        val api = UserApi(osmConnection)
        runBlocking {
            val userInfo = api.getMine()

            assertEquals("Naresh Vindago", userInfo.displayName)
            assertEquals(1, userInfo.id)
        }
    }

    @Test
    fun testGetUserApi() {
        val osmConnection = OSMConnection(posmBase, "OSMAPI")
        val api = UserApi(osmConnection)
        runBlocking {
            val userInfo = api.get(2)

            assertEquals("Rajesh K", userInfo?.displayName)
            assertEquals(2, userInfo?.id)
        }
    }

    @Test
    fun testGetAllUserApi() {
        val osmConnection = OSMConnection(posmBase, "OSMAPI")
        val api = UserApi(osmConnection)
        runBlocking {
            val userInfo = api.getAll(listOf(1,2,3))

            assertEquals(3, userInfo.size)
        }
    }
}
package com.example.osmapi.changeset

import com.example.osmapi.core.OSMConnection
import com.example.osmapi.core.capabilities.CapabilitiesApi
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ChangesetApiTests {

    val changesetId: Long = 55
    private val posmBase = "https://waylyticsposm.westus2.cloudapp.azure.com/api/0.6/"
    @Test
    fun testGet() {
        val osmConnection = OSMConnection(posmBase,"OSMAPI")
        val api = ChangesetsApi(osmConnection)
        runBlocking {
            val changeset = api.get(changesetId)
            assertFalse { changeset.isOpen }
            assertTrue { changeset.discussion.isNotEmpty() }
        }
    }
    @Test
    fun testComment(){
        val osmConnection = OSMConnection(posmBase,"OSMAPI")
        val api = ChangesetsApi(osmConnection)
        runBlocking {
            val changeset = api.comment(changesetId,"Test comment from user")
            assertFalse { changeset.isOpen }
        }
    }
}
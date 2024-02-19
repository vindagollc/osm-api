package com.vindago.osmapi.changeset

import com.vindago.osmapi.core.OSMConnection
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
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
            assertNotNull(changeset)
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

    @Test
    fun testSubscribe(){
        val osmConnection = OSMConnection(posmBase,"OSMAPI")
        val api = ChangesetsApi(osmConnection)
        runBlocking {
            val changeset = api.subscribe(changesetId)
            assertNotNull(changeset)
            assertFalse { changeset.isOpen }
        }
    }
}
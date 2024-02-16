package com.vindago.osmapi.core.capabilities

import com.vindago.osmapi.core.OSMConnection
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class CapabilitiesApiTests {
    private val posmBase = "https://waylyticsposm.westus2.cloudapp.azure.com/api/0.6/"

    @Test
    fun testCapabilitiesApi() {
        val osmConnection = OSMConnection(posmBase,"OSMAPI")
        val api = CapabilitiesApi(osmConnection)
        runBlocking {
            val capabilities = api.get()
            assertEquals(capabilities.maxMapQueryAreaInSquareDegrees,0.25F)
        }
    }
}
package com.vindago.osmapi.core.capabilities

import com.vindago.osmapi.core.OSMConnection

class CapabilitiesApi(val osm: OSMConnection) {

    suspend fun get() : Capabilities {
        // Get the response and parse it
        return osm.fetch("capabilities", CapabiltiesParser())
    }
}
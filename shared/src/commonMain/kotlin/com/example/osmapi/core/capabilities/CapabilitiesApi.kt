package com.example.osmapi.core.capabilities

import com.example.osmapi.core.OSMConnection

class CapabilitiesApi(val osm: OSMConnection) {

    suspend fun get() : Capabilities {
        // Get the response and parse it
        return osm.fetch("capabilities", CapabiltiesParser())
    }
}
package com.example.osmapi

import io.ktor.client.HttpClient

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import  nl.adaptivity.xmlutil.serialization.XML

class OSMAPI {

    private val client = HttpClient()

    private val baseUrl = "https://api.openstreetmap.org/api/0.6/"

    private val localResponseText = """
        <?xml version="1.0" encoding="UTF-8"?>
        <osm version="0.6" generator="OpenStreetMap server" copyright="OpenStreetMap and contributors" attribution="http://www.openstreetmap.org/copyright" license="http://opendatacommons.org/licenses/odbl/1-0/">
          <api>
            <version minimum="0.6" maximum="0.6"/>
            <area maximum="0.25"/>
            <note_area maximum="25"/>
            <tracepoints per_page="5000"/>
            <waynodes maximum="2000"/>
            <relationmembers maximum="32000"/>
            <changesets maximum_elements="10000" default_query_limit="100" maximum_query_limit="100"/>
            <notes default_query_limit="100" maximum_query_limit="10000"/>
            <timeout seconds="300"/>
            <status database="online" api="online" gpx="online"/>
          </api>
          <policy>
            <imagery>
              <blacklist regex=".*\.google(apis)?\..*/.*"/>
              <blacklist regex="http://xdworld\.vworld\.kr:8080/.*"/>
              <blacklist regex=".*\.here\.com[/:].*"/>
              <blacklist regex=".*\.mapy\.cz.*"/>
            </imagery>
          </policy>
        </osm>
    """.trimIndent()


    suspend fun getData(): String {
    // capaibilities
        val url = baseUrl+"capabilities"
        val response = client.get(url)
        return response.bodyAsText()
        
    }

    suspend fun getCapabilities():String {
        val url = baseUrl+"capabilities"
        val response = client.get(url)
        print(response.bodyAsText())
        val body = XML.decodeFromString(Capabilities.serializer(), response.bodyAsText())
        return body.api.version.minimum
    }
    fun getLocalCapabilities(): String {
        val body = XML.decodeFromString(Capabilities.serializer(), localResponseText)
        return body.api.version.minimum
    }
}
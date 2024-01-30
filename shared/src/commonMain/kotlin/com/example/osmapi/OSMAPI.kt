package com.example.osmapi

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import nl.adaptivity.xmlutil.util.SerializationProvider
import  nl.adaptivity.xmlutil.serialization.XML

class OSMAPI {

    private val client = HttpClient()

    private val baseUrl = "https://api.openstreetmap.org/api/0.6/"

    suspend fun getData(): String {
    // capaibilities
        val url = baseUrl+"capabilities"
        val response = client.get(url)
        return response.bodyAsText()
        
    }

    fun testParsing(){

    }

    suspend fun getCapabilities():String {
        val url = baseUrl+"capabilities"
        val response = client.get(url)
        return response.bodyAsText()
    }
}
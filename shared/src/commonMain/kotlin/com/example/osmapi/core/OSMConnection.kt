package com.example.osmapi.core

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText


class OSMConnection(private var apiUrl: String,
                    private var userAgent: String,
                    private var oauthToken: String? = null,
                    private var timeout: Int = DEFAULT_TIMEOUT) {

    private val client = HttpClient()

    fun setTimeout(timeout: Int){
        this.timeout = timeout
    }

    suspend fun get(path:String): String {
        val url = apiUrl.plus(path)
        val response = client.get(url){
            setTimeout(timeout)
        }
        return response.bodyAsText()
    }

    suspend fun <T> fetch(path:String, reader: APIResponseReader<T>) : T {
        val url = apiUrl.plus(path)
        val response = client.get(url){
            setTimeout(timeout)
        }
        return reader.parse(response.bodyAsText())
    }



    companion object{
        private  const val DEFAULT_TIMEOUT: Int = 45*1000
    }
}
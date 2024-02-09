package com.example.osmapi.core

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
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

    suspend fun <T> post(path: String, reader: APIResponseReader<T>) :T { // May have to add another function or parameter to accept payload
        val url = apiUrl.plus(path)
        val response = client.post(url){
            setTimeout(timeout)
            header("Authorization","Basic bmFyZXNoZEB2aW5kYWdvLmluOmEkaHdhN2hhbUE")
        }
        return reader.parse(response.bodyAsText())
    }


    suspend fun <T> fetchAuthenticated(path:String, reader: APIResponseReader<T>) : T {
        //header("Authorization","Basic bmFyZXNoZEB2aW5kYWdvLmluOmEkaHdhN2hhbUE")
        val url = apiUrl.plus(path)
        val response = client.get(url){
            setTimeout(timeout)
            header("Authorization","Basic bmFyZXNoZEB2aW5kYWdvLmluOmEkaHdhN2hhbUE")
        }
        return reader.parse(response.bodyAsText())
    }



    companion object{
        private  const val DEFAULT_TIMEOUT: Int = 45*1000
    }
}
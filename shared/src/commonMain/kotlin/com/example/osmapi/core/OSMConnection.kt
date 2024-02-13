package com.example.osmapi.core

import com.example.osmapi.core.common.errors.OsmConnectionException
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.charsets.*
import io.ktor.utils.io.errors.*


class OSMConnection(
    private var apiUrl: String,
    private var userAgent: String,
    private var oauthToken: String? = null,
    private var timeout: Int = DEFAULT_TIMEOUT
) {

    private val client = HttpClient()

    fun setTimeout(timeout: Int) {
        this.timeout = timeout
    }

    suspend fun get(path: String): String {
        val url = apiUrl.plus(path)
        val response = client.get(url) {
            setTimeout(timeout)
        }
        return response.bodyAsText()
    }

    suspend fun <T> fetch(path: String, reader: APIResponseReader<T>): T {
        try {
            val url = apiUrl.plus(path)
            val response = client.get(url) {
                setTimeout(timeout)
            }
            handleResponseCode(response)
            return reader.parse(response.bodyAsText())
        } catch (e: IOException) {
            throw OsmConnectionException(cause = e)
        }
    }

    suspend fun <T> post(
        path: String,
        reader: APIResponseReader<T>
    ): T { // May have to add another function or parameter to accept payload
        val url = apiUrl.plus(path)
        val response = client.post(url) {
            setTimeout(timeout)
            header("Authorization", "Basic bmFyZXNoZEB2aW5kYWdvLmluOmEkaHdhN2hhbUE")
        }
        handleResponseCode(response)
        return reader.parse(response.bodyAsText())
    }


    suspend fun <T> fetchAuthenticated(path: String, reader: APIResponseReader<T>): T {
        //header("Authorization","Basic bmFyZXNoZEB2aW5kYWdvLmluOmEkaHdhN2hhbUE")
        val url = apiUrl.plus(path)
        val response = client.get(url) {
            setTimeout(timeout)
            header("Authorization", "Basic bmFyZXNoZEB2aW5kYWdvLmluOmEkaHdhN2hhbUE")
        }
        return reader.parse(response.bodyAsText())
    }

    private suspend fun handleResponseCode(httpResponse: HttpResponse) {
        if (httpResponse.status != HttpStatusCode.OK) {
            val errorBody = httpResponse.bodyAsText(Charsets.UTF_8)

            throw OsmApiErrorFactory.createError(httpResponse.status, errorBody, errorBody)
        }
    }


    companion object {
        private const val DEFAULT_TIMEOUT: Int = 45 * 1000
    }
}
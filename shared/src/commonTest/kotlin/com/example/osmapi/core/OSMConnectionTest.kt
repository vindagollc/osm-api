package com.example.osmapi.core

import com.example.osmapi.core.capabilities.CapabiltiesParser
import com.example.osmapi.core.common.errors.*
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertFailsWith

class OSMConnectionTest {

    private val posmBase = "https://waylyticsposm.westus2.cloudapp.azure.com/api/0.6/"

    @Test
    fun `test handleResponseCode for HttpStatusCode-NotFound`() {
        val apiClient = OSMConnection(
            posmBase, "OSMAPI", client = HttpClient(
                getMockEngineWithHttpStatusCode(
                    HttpStatusCode.NotFound
                )
            )
        )
        runBlocking {
            assertFailsWith<OsmNotFoundException> {
                apiClient.fetch("", CapabiltiesParser())
            }
        }
    }

    @Test
    fun `test handleResponseCode for HttpStatusCode-Forbidden`() {
        val apiClient = OSMConnection(
            posmBase, "OSMAPI", client = HttpClient(
                getMockEngineWithHttpStatusCode(
                    HttpStatusCode.Forbidden
                )
            )
        )
        runBlocking {
            assertFailsWith<OsmAuthorizationException> {
                apiClient.fetch("", CapabiltiesParser())
            }
        }
    }

    @Test
    fun `test handleResponseCode for HttpStatusCode-Unauthorized`() {
        val apiClient = OSMConnection(
            posmBase, "OSMAPI", client = HttpClient(
                getMockEngineWithHttpStatusCode(
                    HttpStatusCode.Unauthorized
                )
            )
        )
        runBlocking {
            assertFailsWith<OsmAuthorizationException> {
                apiClient.fetch("", CapabiltiesParser())
            }
        }
    }

    @Test
    fun `test handleResponseCode for HttpStatusCode-PreconditionFailed`() {
        val apiClient = OSMConnection(
            posmBase, "OSMAPI", client = HttpClient(
                getMockEngineWithHttpStatusCode(
                    HttpStatusCode.PreconditionFailed
                )
            )
        )
        runBlocking {
            assertFailsWith<OsmPreconditionFailedException> {
                apiClient.fetch("", CapabiltiesParser())
            }
        }
    }

    @Test
    fun `test handleResponseCode for HttpStatusCode-Conflict`() {
        val apiClient = OSMConnection(
            posmBase, "OSMAPI", client = HttpClient(
                getMockEngineWithHttpStatusCode(
                    HttpStatusCode.Conflict
                )
            )
        )
        runBlocking {
            assertFailsWith<OsmConflictException> {
                apiClient.fetch("", CapabiltiesParser())
            }
        }
    }

    @Test
    fun `test handleResponseCode for HttpStatusCode-BadRequest`() {
        val apiClient = OSMConnection(
            posmBase, "OSMAPI", client = HttpClient(
                getMockEngineWithHttpStatusCode(
                    HttpStatusCode.BadRequest
                )
            )
        )
        runBlocking {
            assertFailsWith<OsmBadUserInputException> {
                apiClient.fetch("", CapabiltiesParser())
            }
        }
    }

    @Test
    fun `test handleResponseCode for HttpStatusCode-TooManyRequests`() {
        val apiClient = OSMConnection(
            posmBase, "OSMAPI", client = HttpClient(
                getMockEngineWithHttpStatusCode(
                    HttpStatusCode.TooManyRequests
                )
            )
        )
        runBlocking {
            assertFailsWith<OsmTooManyRequestsException> {
                apiClient.fetch("", CapabiltiesParser())
            }
        }
    }

    @Test
    fun `test handleResponseCode for HttpStatusCode-createGenericError-400to500`() {
        val apiClient = OSMConnection(
            posmBase, "OSMAPI", client = HttpClient(
                getMockEngineWithHttpStatusCode(
                    HttpStatusCode.FailedDependency
                )
            )
        )
        runBlocking {
            assertFailsWith<OsmApiException> {
                apiClient.fetch("", CapabiltiesParser())
            }
        }
    }

    @Test
    fun `test handleResponseCode for HttpStatusCode-createGenericError-above500`() {
        val apiClient = OSMConnection(
            posmBase, "OSMAPI", client = HttpClient(
                getMockEngineWithHttpStatusCode(
                    HttpStatusCode.VersionNotSupported
                )
            )
        )
        runBlocking {
            assertFailsWith<OsmConnectionException> {
                apiClient.fetch("", CapabiltiesParser())
            }
        }
    }

    @Test
    fun `test handleResponseCode for HttpStatusCode-createGenericError-below400`() {
        val apiClient = OSMConnection(
            posmBase, "OSMAPI", client = HttpClient(
                getMockEngineWithHttpStatusCode(
                    HttpStatusCode.SwitchProxy
                )
            )
        )
        runBlocking {
            assertFailsWith<OsmConnectionException> {
                apiClient.fetch("", CapabiltiesParser())
            }
        }
    }

    private fun getMockEngineWithHttpStatusCode(statusCode: HttpStatusCode = HttpStatusCode.OK): MockEngine {
        return MockEngine {
            respond(
                content = ByteReadChannel("""Content"""),
                status = statusCode,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
    }
}
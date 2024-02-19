package com.vindago.osmapi.core

import com.vindago.osmapi.core.common.errors.*
import io.ktor.http.*


class OsmApiErrorFactory {
    companion object {
        fun createError(error: HttpStatusCode, response: String, description: String): RuntimeException {
            return when (error) {
                HttpStatusCode.ServiceUnavailable -> OsmServiceUnavailableException(error.value, response, description)
                HttpStatusCode.NotFound, HttpStatusCode.Gone ->
                    OsmNotFoundException(error.value, response, description)

                HttpStatusCode.Forbidden, HttpStatusCode.Unauthorized ->
                    OsmAuthorizationException(error.value, response, description)

                HttpStatusCode.PreconditionFailed -> OsmPreconditionFailedException(error.value, response, description)
                HttpStatusCode.Conflict -> OsmConflictException(error.value, response, description)
                HttpStatusCode.BadRequest -> OsmBadUserInputException(error.value, response, description)
                HttpStatusCode.TooManyRequests -> OsmTooManyRequestsException(error.value, response, description)
                else -> createGenericError(error.value, response, description)
            }
        }

        private fun createGenericError(error: Int, response: String, description: String): RuntimeException {
            if (error in 400..499) {
                // "The 4xx class of status code is intended for situations in which the client seems to have erred"
                return OsmApiException(error, response, description)
            }
            // "The server failed to fulfill an apparently valid request"
            return OsmConnectionException(error, response, description)
        }
    }
}
package com.example.osmapi.core.common.errors

class OsmTooManyRequestsException(
    errorCode: Int = 0,
    errorTitle: String = "",
    description: String = ""
) : OsmConnectionException(errorCode, errorTitle, description)

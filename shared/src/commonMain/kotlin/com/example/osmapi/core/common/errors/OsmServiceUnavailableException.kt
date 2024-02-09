package com.example.osmapi.core.common.errors

class OsmServiceUnavailableException(
    errorCode: Int = 0,
    errorTitle: String = "",
    description: String = ""
) : OsmConnectionException(errorCode = errorCode, errorTitle = errorTitle, description = description)
package com.vindago.osmapi.core.common.errors

class OsmNotFoundException(
    errorCode: Int = 0,
    errorTitle: String = "",
    description: String = ""
) : OsmConnectionException(errorCode = errorCode, errorTitle = errorTitle, description = description)
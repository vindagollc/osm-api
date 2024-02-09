package com.example.osmapi.core.common.errors

open class OsmBadUserInputException(
    errorCode: Int = 0,
    errorTitle: String = "",
    description: String = ""
) : OsmConnectionException(errorCode, errorTitle, description)
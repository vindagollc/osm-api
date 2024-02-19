package com.vindago.osmapi.core.common.errors

class OsmPreconditionFailedException(
    errorCode: Int = 0,
    errorTitle: String = "",
    description: String = ""
) : OsmConnectionException(errorCode, errorTitle, description)
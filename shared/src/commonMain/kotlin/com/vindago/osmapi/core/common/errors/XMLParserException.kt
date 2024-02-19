package com.vindago.osmapi.core.common.errors

class XMLParserException(
    cause: Throwable? = null,
    val reason: String = ""
) : RuntimeException(cause)
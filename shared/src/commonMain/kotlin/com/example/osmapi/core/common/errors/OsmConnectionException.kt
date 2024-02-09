package com.example.osmapi.core.common.errors

open class OsmConnectionException(
    cause: Throwable? = null,
    val errorCode: Int = 0,
    val errorTitle: String = "",
    val description: String = ""
) : RuntimeException(cause) {
}
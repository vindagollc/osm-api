package com.example.osmapi.core.common.errors

open class OsmConnectionException(
    val errorCode: Int = 0,
    val errorTitle: String = "",
    val description: String = "",
    cause: Throwable? = null,
    ) : RuntimeException(cause) {
}
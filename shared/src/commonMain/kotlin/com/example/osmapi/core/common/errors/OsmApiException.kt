package com.example.osmapi.core.common.errors
//

open class OsmApiException(
    cause: Throwable? = null,
    val errorCode: Int = 0,
    val errorTitle: String = "",
    val description: String = ""
) : RuntimeException(cause){
    override fun toString(): String {
        if (cause != null) {return super.toString()}
        return "Custom error"
    }
}

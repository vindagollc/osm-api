package com.vindago.osmapi.core.common.errors
//

open class OsmApiException(
    val errorCode: Int = 0,
    val errorTitle: String = "",
    val description: String = "",
    cause: Throwable? = null,
) : RuntimeException(cause){
    override fun toString(): String {
        if (cause != null) {return super.toString()}
        return "Custom error"
    }
}

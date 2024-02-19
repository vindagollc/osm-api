package com.vindago.osmapi

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
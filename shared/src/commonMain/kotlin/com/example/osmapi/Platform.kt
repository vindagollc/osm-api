package com.example.osmapi

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
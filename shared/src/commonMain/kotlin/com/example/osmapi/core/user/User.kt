package com.example.osmapi.core.user

import kotlinx.serialization.Serializable

@Serializable
open class User(val id:Long, val displayName: String)
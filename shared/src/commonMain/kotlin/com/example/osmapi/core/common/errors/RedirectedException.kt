package com.example.osmapi.core.common.errors

import io.ktor.utils.io.errors.IOException

open class RedirectedException(reason:String) : IOException(reason) {
}
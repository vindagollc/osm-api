package com.example.osmapi.core.common.errors

import io.ktor.utils.io.errors.IOException

class RedirectedException(reason:String) : IOException(message=reason) {
}
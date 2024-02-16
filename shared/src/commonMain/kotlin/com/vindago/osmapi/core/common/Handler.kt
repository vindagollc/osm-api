package com.vindago.osmapi.core.common

interface Handler<T> {
    // Called when a new object is created from input stream.
    fun handle(tea: T)
}
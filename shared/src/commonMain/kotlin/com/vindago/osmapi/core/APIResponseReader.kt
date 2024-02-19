package com.vindago.osmapi.core

interface APIResponseReader<T> {
    fun parse(xmlString: String): T
}
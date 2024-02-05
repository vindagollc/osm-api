package com.example.osmapi.core

interface APIResponseReader<T> {
    fun parse(xmlString: String): T
}
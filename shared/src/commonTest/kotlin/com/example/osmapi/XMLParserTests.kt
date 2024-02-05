package com.example.osmapi

import kotlin.test.Test

class XMLParserTests {
    private val xmlParser = LocalXMLParser()

    @Test
    fun testRemoteUser() {

        val result = xmlParser.main()
        print(result)
    }

}
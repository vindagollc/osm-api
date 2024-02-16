package com.vindago.osmapi.changeset

import nl.adaptivity.xmlutil.serialization.XML
import kotlin.test.Test
import kotlin.test.assertTrue

class ChangesetPayloadTests {

    @Test
    fun testPayload(){
        val payload = ChangesetPayload("First comment","Second Comment")
        val xmlPayload = XML.Companion.encodeToString(payload)
        print(xmlPayload)
        assertTrue(xmlPayload.isNotEmpty())
    }
}
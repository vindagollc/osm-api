package com.example.osmapi.core.user

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PermissionsParserTest {

    @Test
    fun permissionParser() {
        val xml = """
            <osm version="0.6" generator="OpenStreetMap server">
            	<permissions>
            		<permission name="allow_read_prefs"/>
            		<permission name="allow_read_gpx"/>
            		<permission name="allow_write_gpx"/>
            	</permissions>
            </osm>
        """.trimIndent()
        val permissions = PermissionsParser().parse(xml)
        assertTrue { permissions.contains("allow_read_prefs") }
        assertTrue { permissions.contains("allow_read_gpx") }
        assertFalse { permissions.contains("allow2_read_prefs") }
    }
}
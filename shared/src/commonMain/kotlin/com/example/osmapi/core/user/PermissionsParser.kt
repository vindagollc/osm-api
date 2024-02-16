package com.example.osmapi.core.user

import com.example.osmapi.core.APIResponseReader
import com.example.osmapi.core.common.XMLParser

/**
 * <?xml version="1.0" encoding="UTF-8"?>
 * <osm version="0.6" generator="OpenStreetMap server">
 * 	<permissions>
 * 		<permission name="allow_read_prefs"/>
 * 		...
 * 		<permission name="allow_read_gpx"/>
 * 		<permission name="allow_write_gpx"/>
 * 	</permissions>
 * </osm>
 */
/** Parses the permissions this osm user has on this server (API 0.6). */
class PermissionsParser: XMLParser(), APIResponseReader<List<String>> {

    companion object{
        const val PERMISSIONS = "permissions"
    }
    var permissions: MutableList<String> = mutableListOf()
    override fun parse(xmlString: String): List<String> {
        doParse(xmlString)
        return permissions
    }

    override fun onStartElement(name: String, path: String, attributes: Map<String, String>) {
        if("permission" == name) {
            permissions.add(attributes["name"]!!) // Make a check here somewhere.
        }
    }

    override fun onEndElement(name: String, path: String) {
        // Do nothing
    }

    override fun onText(text: String) {
        // Do nothing
    }
}
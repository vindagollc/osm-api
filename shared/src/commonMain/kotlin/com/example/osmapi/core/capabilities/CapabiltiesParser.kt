package com.example.osmapi.core.capabilities

import com.example.osmapi.core.APIResponseReader
import com.example.osmapi.core.common.XMLParser

// Specific class for parsing the Capabilities
// All the needed elements are there in the XML
/**
 * Example Response for parser
 *
 */
class CapabiltiesParser: XMLParser() , APIResponseReader<Capabilities> {

    private lateinit var capabilities: Capabilities
    override fun onStartElement(
        name: String,
        path: String,
        attributes: Map<String, String>
    ) {
        if(path.contains("api>version")){
            // Parse apiVersion
            parseAPIVersions(attributes)
        }
        if(path.contains("api>area")){
            parseMaxQueryArea(attributes)
        }
        if(path.contains("api>note_area")){
            // Parse maxNoteQueryArea
            capabilities.maxNotesQueryAreaInSquareDegrees = attributes["maximum"]?.toFloat() ?: 0.0F
        }
        if(path.contains("api>tracepoints")){
            // parse maxPointsInGpsTracePerPage
            capabilities.maxPointsInGpsTracePerPage = attributes["maximum"]?.toInt() ?: 0
        }
        if(path.contains("api>waynodes")){
            capabilities.maxNodsInWay = attributes["maximum"]?.toInt() ?: 0
        }
        if (path.contains("api>relationmembers")){
            capabilities.maxMembersInRelation = attributes["maximum"]?.toInt() ?: 0
        }
        if(path.contains("api>changesets")){
            capabilities.maxElementsPerChangeset = attributes["maximum_elements"]?.toInt() ?: 0
            capabilities.defaultChangesetsQueryLimit = attributes["default_query_limit"]?.toInt() ?: 0
            capabilities.maximumChangesetsQueryLimit = attributes["maximum_query_limit"]?.toInt() ?: 0
        }
        if(path.contains("api>timeout")) {
            capabilities.timeoutInSeconds = attributes["seconds"]?.toInt() ?: 0
        }
        if(path.contains("api>status")) {
            capabilities.databaseStatus = Capabilities.parseApiStatus(attributes["database"]?:"")
            capabilities.mapDataStatus = Capabilities.parseApiStatus(attributes["api"]?:"")
            capabilities.gpsTracesStatus = Capabilities.parseApiStatus(attributes["gpx"]?:"")
        }
        if(path.contains("api>notes")){
            capabilities.defaultChangesetsQueryLimit = attributes["default_query_limit"]?.toInt() ?: 0
            capabilities.maximumNotesQueryLimit = attributes["maximum_query_limit"]?.toInt() ?: 0
        }
    }

    private fun parseAPIVersions(apiAttributes: Map<String, String>){
        capabilities.minSupportedApiVersion = apiAttributes["minimum"]?.toFloat() ?: 0.0F
        capabilities.maxSupportedApiVersion = apiAttributes["maximum"]?.toFloat() ?: 0.0F
    }

    private fun parseMaxQueryArea(apiAttributes: Map<String, String>){
        capabilities.maxMapQueryAreaInSquareDegrees = apiAttributes["maximum"]?.toFloat() ?: 0.0F
    }



    override fun onEndElement(name: String, path: String) {
        println("Ended "+name)
    }

    override fun onText(text: String) {
        println("Text received")
    }

    override fun parse(xmlString: String): Capabilities {
        capabilities = Capabilities()
         doParse(xmlString)
        return capabilities
    }
}
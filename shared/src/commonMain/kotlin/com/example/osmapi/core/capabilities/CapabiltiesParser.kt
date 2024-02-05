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
        attributes: List<Pair<String, String>>
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
        }
        if(path.contains("api>tracepoints")){
            // parse maxPointsInGpsTracePerPage
        }
    }

    private fun parseAPIVersions(apiAttributes: List<Pair<String, String>>){
        apiAttributes.forEach {
            if(it.first.equals("minimum")){
                capabilities.minSupportedApiVersion = it.second.toFloat()
            }
            if(it.first.equals("maximum")){
                capabilities.maxSupportedApiVersion = it.second.toFloat()
            }
        }
    }

    private fun parseMaxQueryArea(apiAttributes: List<Pair<String, String>>){

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
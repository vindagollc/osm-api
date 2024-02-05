package com.example.osmapi.core.common

import nl.adaptivity.xmlutil.EventType
import nl.adaptivity.xmlutil.XmlReader
import nl.adaptivity.xmlutil.attributes
import nl.adaptivity.xmlutil.xmlStreaming

/**
 * Generic data parser for osm purpose
 */
abstract class XMLParser {

    var pathHandler: MutableList<String> = mutableListOf()
    var currentDepth: Int = 0
    val pathSeparater = ">"

    val xPath get() = pathHandler.joinToString(pathSeparater)

    fun doParse(incoming: String){
        // Loop till the end of the document
        val reader : XmlReader = xmlStreaming.newGenericReader(incoming)
        while ( reader.hasNext()){
            val event = reader.next()
            // use when here.
            if (event == EventType.START_ELEMENT){

                pathHandler.add(reader.name.getLocalPart())
//                if(reader.depth > currentDepth){
//                    currentDepth = reader.depth
//                    pathHandler.add(reader.name.getLocalPart())
//                }
//                else if(reader.depth < currentDepth){
//                    currentDepth = reader.depth
//                    pathHandler.removeLast()
//                }
//                else if(reader.depth == currentDepth){
//                     pathHandler.removeLast()
//                    pathHandler.add(reader.name.getLocalPart())
//                }
                // Get the attributes and loop them
                // Generate the key value map
                val attributeMap = reader.attributes.map { it.localName to it.value }.toMap()
                onStartElement(reader.localName,xPath,attributeMap)
            }
            if (event == EventType.END_ELEMENT){
                pathHandler.removeLast()
                // send out end element with name and path
                onEndElement(reader.localName,xPath)
            }
            if(event == EventType.TEXT){
                // send out text element with name and path
                onText(reader.text)
            }
        }
    }

    abstract fun onStartElement(name: String, path: String, attributes: Map<String, String>) // TODO: Change the type for attributes

    abstract fun onEndElement(name: String, path: String)
    abstract fun onText(text: String)
}
package com.vindago.osmapi

import nl.adaptivity.xmlutil.EventType
import nl.adaptivity.xmlutil.XmlReader
import nl.adaptivity.xmlutil.xmlStreaming


class LocalXMLParser {
    val stack = mutableListOf<XmlElement>()

    private fun parse(xmlReader: XmlReader) {
        xmlReader.next()
        var eventType = xmlReader.eventType


        while (eventType != EventType.END_DOCUMENT) {
            when (eventType) {
                EventType.START_ELEMENT -> {
                    val element = XmlElement(xmlReader.localName, mutableMapOf())
                    for (i in 0 until xmlReader.attributeCount) {
                        element.attributes[xmlReader.getAttributeLocalName(i)] = xmlReader.getAttributeValue(i)
                    }
                    stack.lastOrNull()?.children?.add(element)
                    stack.add(element)
                }
                EventType.END_ELEMENT -> {
                    //stack.removeAt(stack.size - 1)
                }
                EventType.TEXT -> {
                    val text = xmlReader.text?.trim()
                    if (!text.isNullOrBlank()) {
                        stack.lastOrNull()?.text = text
                    }
                }

                else -> {
                    print("Error")
                }
            }

            eventType = xmlReader.next()
        }
    }

    fun getRootElement(): XmlElement? = stack.firstOrNull()

    fun main() {
        val xmlString = """
        <root>
            <person>
                <name>John Doe</name>
                <age>30</age>
            </person>
            <person>
                <name>Jane Doe</name>
                <age>25</age>
            </person>
        </root>
    """.trimIndent()


        val reader : XmlReader = xmlStreaming.newGenericReader(xmlString)

        val parser = LocalXMLParser()
        parser.parse(reader)

        val elements = parser.stack
        println(elements)
    }
}

data class XmlElement(val name: String, val attributes: MutableMap<String, String>, val children: MutableList<XmlElement> = mutableListOf(), var text: String? = null)


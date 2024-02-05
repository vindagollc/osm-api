package com.example.osmapi.core.common

import kotlin.test.Test

class XMLParserTests {

    val incomingString = """
        <root>
            <person noname="hello">
                <name>John Doe</name>
                <age>30</age>
            </person>
            <person noname="hello">
                <name>Jane Doe</name>
                <age>25</age>
            </person>
        </root>
    """.trimIndent()

    private val localResponseText = """
        <?xml version="1.0" encoding="UTF-8"?>
        <osm version="0.6" generator="OpenStreetMap server" copyright="OpenStreetMap and contributors" attribution="http://www.openstreetmap.org/copyright" license="http://opendatacommons.org/licenses/odbl/1-0/">
          <api>
            <version minimum="0.6" maximum="0.6"/>
            <area maximum="0.25"/>
            <note_area maximum="25"/>
            <tracepoints per_page="5000"/>
            <waynodes maximum="2000"/>
            <relationmembers maximum="32000"/>
            <changesets maximum_elements="10000" default_query_limit="100" maximum_query_limit="100"/>
            <notes default_query_limit="100" maximum_query_limit="10000"/>
            <timeout seconds="300"/>
            <status database="online" api="online" gpx="online"/>
          </api>
          <policy>
            <imagery>
              <blacklist regex=".*\.google(apis)?\..*/.*"/>
              <blacklist regex="http://xdworld\.vworld\.kr:8080/.*"/>
              <blacklist regex=".*\.here\.com[/:].*"/>
              <blacklist regex=".*\.mapy\.cz.*"/>
            </imagery>
          </policy>
        </osm>
    """.trimIndent()
    @Test
    fun testDataParser(){
        val parser = GeneralParser()
        parser.doParse(localResponseText)

    }
}

class GeneralParser : XMLParser() {
    override fun onStartElement(
        name: String,
        path: String,
        attributes: List<Pair<String, String>>
    ) {
         println("Started element")
         print(name)
        print(attributes)
    }

    override fun onEndElement(name: String, path: String) {
            println("Ended element")
            println(name)
    }

    override fun onText(text: String) {
        println("Text for element")
        println(text)
    }

}
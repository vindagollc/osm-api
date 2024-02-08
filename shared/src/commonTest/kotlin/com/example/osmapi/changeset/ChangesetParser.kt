package com.example.osmapi.changeset

import com.example.osmapi.core.common.Handler
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.asserter

class ChangesetParserTests {
    // Tests the parser stuff for changeset
    val testXML = """
        <osm>
        	<changeset id="10" created_at="2008-11-08T19:07:39+01:00" open="true" user="fred" uid="123" min_lon="7.0191821" min_lat="49.2785426" max_lon="7.0197485" max_lat="49.2793101" comments_count="3" changes_count="10">
        		<tag k="created_by" v="JOSM 1.61"/>
        		<tag k="comment" v="Just adding some streetnames"/>
        		<discussion>
        			<comment date="2015-01-01T18:56:48Z" uid="1841" user="metaodi">
        				<text>Did you verify those street names?</text>
        			</comment>
        			<comment date="2015-01-01T18:58:03Z" uid="123" user="fred">
        				<text>sure!</text>
        			</comment>
        		</discussion>
        	</changeset>
        </osm>
    """.trimIndent()

    val test2XML = """
        <?xml version="1.0" encoding="UTF-8"?>
        <osm version="0.6" generator="OpenStreetMap server" copyright="OpenStreetMap and contributors" attribution="http://www.openstreetmap.org/copyright" license="http://opendatacommons.org/licenses/odbl/1-0/">
        <changeset id="55" created_at="2023-12-29T12:06:15Z" open="false" comments_count="0" changes_count="1213" closed_at="2023-12-29T13:29:32Z" min_lat="38.8877171" min_lon="-77.0450395" max_lat="38.9212357" max_lon="-77.0020619" uid="1" user="Naresh Vindago">
          <tag k="comment" v="From AI Engine 2"/>
          <tag k="created_by" v="osmapi/4.0.0"/>
          <discussion>
          </discussion>
        </changeset>
        </osm>

    """.trimIndent()

    @Test
    fun testParser(){
        val handler = ChangesetHandler()
        val parser = ChangesetParser(handler)
        parser.doParse(test2XML)
        assertNotNull(handler.currentChangesetInfo)
        assertEquals(handler.currentChangesetInfo?.discussion?.count(),0)
        assertEquals(handler.currentChangesetInfo?.boundingBox?.getMinLon(),-77.0450395)
        assertTrue { handler.currentChangesetInfo?.tags?.containsKey("created_by") == true }
    }
}

class ChangesetHandler : Handler<ChangesetInfo> {

    var currentChangesetInfo : ChangesetInfo? = null
    override fun handle(tea: ChangesetInfo) {
        currentChangesetInfo = tea
    }

}
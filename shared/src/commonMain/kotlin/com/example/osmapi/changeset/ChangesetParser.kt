package com.example.osmapi.changeset

import com.example.osmapi.core.APIResponseReader
import com.example.osmapi.core.common.Handler
import com.example.osmapi.core.common.XMLParser
import com.example.osmapi.core.map.data.BoundingBox
import com.example.osmapi.core.map.data.OsmLatLon
import com.example.osmapi.core.user.User
import kotlinx.datetime.Instant

/**
 * Single changeset response
 * <osm version="0.6" generator="CGImap 0.8.10 (2227927 spike-07.openstreetmap.org)" copyright="OpenStreetMap and contributors" attribution="http://www.openstreetmap.org/copyright" license="http://opendatacommons.org/licenses/odbl/1-0/">
 * <changeset id="115799290" created_at="2022-01-05T14:05:55Z" closed_at="2022-01-05T14:05:55Z" open="false" user="kbzimmer"
 * uid="6807147" min_lat="46.2681782" min_lon="-119.3058608" max_lat="46.2930964" max_lon="-119.2963945" comments_count="0" changes_count="212">
 * <tag k="changesets_count" v="2060"/>
 * <tag k="comment" v="adding sidewalks and pedestrian paths"/>
 * <tag k="created_by" v="iD 2.20.2"/>
 * <tag k="host" v="https://www.openstreetmap.org/edit"/>
 * <tag k="imagery_used" v="Bing aerial imagery"/>
 * <tag k="locale" v="en"/>
 * <tag k="warnings:disconnected_way:highway" v="1"/>
 * <tag k="warnings:mismatched_geometry:line_as_vertex" v="2"/>
 * </changeset>
 * </osm>
 */
class ChangesetParser(var handler: Handler<ChangesetInfo>): XMLParser() , APIResponseReader<Unit> {

    companion object{
        const val TAG = "tag"
        const val CHANGESET = "changeset"
        const val COMMENT = "comment"
        const val TEXT = "text"
    }

    private  var users: MutableMap<Long,User> = mutableMapOf()

    private var currentChangesetInfo: ChangesetInfo?= null
    override fun parse(xmlString: String) {

    }

    override fun onStartElement(name: String, path: String, attributes: Map<String, String>) {
        if(name == CHANGESET){
            // parse changesetInfo from attributes
            currentChangesetInfo = ChangesetInfo(0, User())
            currentChangesetInfo!!.user = parseUser(attributes)

        }

    }

    private fun parseChangeset(attributes: Map<String, String>) : ChangesetInfo {
        var boundingBox: BoundingBox = BoundingBox(0.0,0.0,0.0,0.0)

        if(attributes["min_lat"] != null){
            boundingBox = BoundingBox(OsmLatLon.parseLatLon(attributes["min_lat"]!!,attributes["min_lon"]!!),
                OsmLatLon.parseLatLon(attributes["max_lat"]!!,attributes["max_lon"]!!))
        }
        val closedAtStr = attributes["closed_at"]
        if(closedAtStr != null){
            Instant.parse(closedAtStr)
        }


        val result = ChangesetInfo(0,User())

        return result
    }

    private fun parseUser(attributes: Map<String, String>) : User{
        val cUser = User()
        cUser.id = attributes["uid"]?.toLong() ?: 0
        cUser.displayName = attributes["user"] ?: ""
        return cUser
    }

    override fun onEndElement(name: String, path: String) {

    }

    override fun onText(text: String) {

    }
}
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
 * 	<discussion>
 * 			<comment date="2015-01-01T18:56:48Z" uid="1841" user="metaodi">
 * 				<text>Did you verify those street names?</text>
 * 			</comment>
 * 			<comment date="2015-01-01T18:58:03Z" uid="123" user="fred">
 * 				<text>sure!</text>
 * 			</comment>
 * 			...
 * 		</discussion>
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
    private var currentComment: ChangesetNote? = null
    private var comments: MutableList<ChangesetNote> = mutableListOf()
    private var commentText: String = ""
    override fun parse(xmlString: String) {
        doParse(xmlString)
    }

    override fun onStartElement(name: String, path: String, attributes: Map<String, String>) {
        if(name == CHANGESET){
            // parse changesetInfo from attributes
            currentChangesetInfo = parseChangeset(attributes)
            currentChangesetInfo!!.user = parseUser(attributes)

        }
        if(name == COMMENT) {
            // parse the comment in the discussion
            // <comment date="2015-01-01T18:58:03Z" uid="123" user="fred">
            currentComment =  parseChangesetComment(attributes)
        }
        if (name == TAG) {
            currentChangesetInfo!!.tags[attributes["k"]!!] = attributes["v"]!!
        }

    }

    private fun parseChangesetComment(attributes: Map<String, String>) : ChangesetNote {
        val user = parseUser(attributes)
        val date = Instant.parse(attributes["date"]!!)
        val comment  = ChangesetNote(user,"",date)
        return comment
    }

    private fun parseChangeset(attributes: Map<String, String>) : ChangesetInfo {
        var boundingBox: BoundingBox = BoundingBox(0.0,0.0,0.0,0.0)

        if(attributes["min_lat"] != null){
            boundingBox = BoundingBox(OsmLatLon.parseLatLon(attributes["min_lon"]!!,attributes["min_lat"]!!),
                OsmLatLon.parseLatLon(attributes["max_lon"]!!,attributes["max_lat"]!!))
        }
        val closedAtStr = attributes["closed_at"]

        val result = ChangesetInfo(0,User(attributes["uid"]?.toLong() ?: 0, attributes["user"] ?: ""))
        result.boundingBox = boundingBox

        if(closedAtStr != null){
            result.closedAt =   Instant.parse(closedAtStr)
        }
        return result
    }

    private fun parseUser(attributes: Map<String, String>) : User{
        val cUser = User(attributes["uid"]?.toLong() ?: 0, attributes["user"] ?: "")
        return cUser
    }

    override fun onEndElement(name: String, path: String) {
            if(name == COMMENT){
                currentComment!!.text = commentText
                // Comment is done. figure it out
                comments.add(currentComment!!)
                currentComment = null
            }
        if (name == CHANGESET){
            currentChangesetInfo!!.discussion = comments
            handler.handle(currentChangesetInfo!!)
            currentChangesetInfo = null
            comments = mutableListOf()
        }
    }

    override fun onText(text: String) {
            commentText = text
    }
}
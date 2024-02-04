package com.example.osmapi.changeset

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import kotlin.jvm.JvmName

/**
 * Payload for opening a payload
 * <osm>
 *                 	<changeset>
 *                 		<tag k="comment" v="Testing adding stuff."/>
 *                 	</changeset>
 *                 </osm>
 */
@Serializable
@XmlSerialName(
    value = "osm"
)
data class ChangesetPayload(
    val comment: ChangesetComment
){
    constructor(vararg  tags:String): this(comment = ChangesetComment(tagStrings = tags) )

}

@Serializable
@XmlSerialName(value = "changeset")
data class ChangesetComment(
    val tags: List<ChangesetTag>
){
    constructor( vararg  tagStrings:String): this(tags = tagStrings.map { ChangesetTag(v=it) })
}
@Serializable
@XmlSerialName(value = "tag")
data class ChangesetTag(
    val k:String = "comment",
    val v:String
)
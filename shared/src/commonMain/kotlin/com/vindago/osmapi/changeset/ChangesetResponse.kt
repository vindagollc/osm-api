package com.vindago.osmapi.changeset

// Class not used anymore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlDefault
import nl.adaptivity.xmlutil.serialization.XmlSerialName

/*
<?xml version="1.0" encoding="UTF-8"?>
<osm version="0.6" generator="OpenStreetMap server" copyright="OpenStreetMap and contributors" attribution="http://www.openstreetmap.org/copyright" license="http://opendatacommons.org/licenses/odbl/1-0/">
<changeset id="73" created_at="2024-01-29T10:54:43Z" open="false" comments_count="0" changes_count="0" closed_at="2024-01-29T11:54:43Z" uid="1" user="Naresh Vindago">
</changeset>
<changeset id="72" created_at="2024-01-29T10:43:44Z" open="false" comments_count="0" changes_count="0" closed_at="2024-01-29T11:43:44Z" uid="1" user="Naresh Vindago">
</changeset>
<changeset id="71" created_at="2024-01-29T09:51:12Z" open="false" comments_count="0" changes_count="0" closed_at="2024-01-29T10:51:12Z" uid="1" user="Naresh Vindago">
</changeset>
<changeset id="70" created_at="2024-01-29T09:47:49Z" open="false" comments_count="0" changes_count="0" closed_at="2024-01-29T10:47:49Z" uid="1" user="Naresh Vindago">
</changeset>
<changeset id="69" created_at="2024-01-29T09:42:39Z" open="false" comments_count="0" changes_count="0" closed_at="2024-01-29T10:42:39Z" uid="1" user="Naresh Vindago">
</changeset>
</osm>
<?xml version="1.0" encoding="UTF-8"?>
<osm version="0.6" generator="OpenStreetMap server" copyright="OpenStreetMap and contributors" attribution="http://www.openstreetmap.org/copyright" license="http://opendatacommons.org/licenses/odbl/1-0/">
<changeset id="73" created_at="2024-01-29T10:54:43Z" open="false" comments_count="0" changes_count="0" closed_at="2024-01-29T11:54:43Z" uid="1" user="Naresh Vindago">
</changeset>
<changeset id="72" created_at="2024-01-29T10:43:44Z" open="false" comments_count="0" changes_count="0" closed_at="2024-01-29T11:43:44Z" uid="1" user="Naresh Vindago">
</changeset>
<changeset id="71" created_at="2024-01-29T09:51:12Z" open="false" comments_count="0" changes_count="0" closed_at="2024-01-29T10:51:12Z" uid="1" user="Naresh Vindago">
</changeset>
<changeset id="70" created_at="2024-01-29T09:47:49Z" open="false" comments_count="0" changes_count="0" closed_at="2024-01-29T10:47:49Z" uid="1" user="Naresh Vindago">
</changeset>
<changeset id="69" created_at="2024-01-29T09:42:39Z" open="false" comments_count="0" changes_count="0" closed_at="2024-01-29T10:42:39Z" uid="1" user="Naresh Vindago">
</changeset>
</osm>
 */
@Serializable
@XmlSerialName(
    value = "osm"
)
data class ChangesetResponse(
    @SerialName("version")
    val osmVersion: String,
    val generator: String,
    val copyright: String,
    val attribution: String,
    val license: String,

    val changesets: List<Changeset>
)

@Serializable
@XmlSerialName("changeset")
data class Changeset(
    val id: String,
    @SerialName("created_at")
    val createdAt:String,
    val open:String,
    @SerialName("comments_count")
    val commentsCount:String,
    @SerialName("closed_at")
    @XmlDefault("null")
    val closedAt:String,
    val uid:String,
    val user: String

)


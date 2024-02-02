package com.example.osmapi

import com.example.osmapi.changeset.Changeset
import com.example.osmapi.changeset.ChangesetResponse
import com.example.osmapi.user.UserResponse
import io.ktor.client.HttpClient

import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import  nl.adaptivity.xmlutil.serialization.XML

class OSMAPI {

    private val client = HttpClient()

    private val baseUrl = "https://api.openstreetmap.org/api/0.6/"
    private val posmBase = "https://waylyticsposm.westus2.cloudapp.azure.com/api/0.6/"

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

    val dummyUserResponse = """
        <?xml version="1.0" encoding="UTF-8"?>
        <osm version="0.6" generator="OpenStreetMap server">
        	<user display_name="Max Muster" account_created="2006-07-21T19:28:26Z" id="1234">
        		<contributor-terms agreed="true" pd="true"/>
        		<img href="https://www.openstreetmap.org/attachments/users/images/000/000/1234/original/someLongURLOrOther.JPG"/>
        		<roles></roles>
        		<changesets count="4182"/>
        		<traces count="513"/>
        		<blocks>
        			<received count="0" active="0"/>
        		</blocks>
        		<home lat="49.4733718952806" lon="8.89285988577866" zoom="3"/>
        		<description>The description of your profile</description>
        		<languages>
        			<lang>de-DE</lang>
        			<lang>de</lang>
        			<lang>en-US</lang>
        			<lang>en</lang>
        		</languages>
        		<messages>
        			<received count="1" unread="0"/>
        			<sent count="0"/>
        		</messages>
        	</user>
        </osm>
    """.trimIndent()

    val dummyChangesetsResponse = """<?xml version="1.0" encoding="UTF-8"?>
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
</osm>""".trimIndent()

    val customDecoder =  XML(){
        defaultPolicy {
            ignoreUnknownChildren()
        }
    }
    suspend fun getData(): String {
    // capaibilities
        val url = baseUrl+"capabilities"
        val response = client.get(url)
        return response.bodyAsText()
        
    }

    suspend fun getCapabilities():String {
        val url = posmBase+"capabilities"
        val response = client.get(url)
        print(response.bodyAsText())
        val body = XML.decodeFromString(Capabilities.serializer(), response.bodyAsText())
        return body.api.version.minimum
    }
    fun getLocalCapabilities(): String {

        val body = XML.decodeFromString(Capabilities.serializer(), localResponseText)
        return body.api.version.minimum
    }

    suspend fun getUser() : String {
        val url = posmBase + "user/details"
        val response = client.get(url){
            header("Authorization","Basic bmFyZXNoZEB2aW5kYWdvLmluOmEkaHdhN2hhbUE")
        }
        val theCoder=  XML(){
            defaultPolicy {
                ignoreUnknownChildren()
            }
        }
        print(response.bodyAsText())
        val body = theCoder.decodeFromString<UserResponse>(response.bodyAsText())
        return body.osmVersion
    }

    fun getLocalUser(): String {
        val thecoder=  XML(){
            defaultPolicy {
                ignoreUnknownChildren()
            }
        }
        val body = thecoder.decodeFromString<UserResponse>(dummyUserResponse)
        return body.osmVersion
    }

    suspend fun getUserChangesets(): List<Changeset> {
        val url = posmBase + "changesets?limit=5"
        val response = client.get(url){
            header("Authorization","Basic bmFyZXNoZEB2aW5kYWdvLmluOmEkaHdhN2hhbUE")
        }
        val changesetResponse = customDecoder.decodeFromString<ChangesetResponse>(response.bodyAsText())
        val body = response.bodyAsText()

        return changesetResponse.changesets
    }

    fun getLocalChangesets(): List<Changeset> {
        val response = customDecoder.decodeFromString<ChangesetResponse>(dummyChangesetsResponse);
        return response.changesets
    }
}
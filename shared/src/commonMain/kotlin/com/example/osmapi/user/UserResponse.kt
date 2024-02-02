package com.example.osmapi.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

/**
 * <user display_name="Max Muster" account_created="2006-07-21T19:28:26Z" id="1234">
 *         		<contributor-terms agreed="true" pd="true"/>
 *         		<img href="https://www.openstreetmap.org/attachments/users/images/000/000/1234/original/someLongURLOrOther.JPG"/>
 *         		<roles>
 *         		 <administrator/>
 *         		 <moderator/>
 *         		</roles>
 *         		<changesets count="4182"/>
 *         		<traces count="513"/>
 *         		<blocks>
 *         			<received count="0" active="0"/>
 *         		</blocks>
 *         		<home lat="49.4733718952806" lon="8.89285988577866" zoom="3"/>
 *         		<description>The description of your profile</description>
 *         		<languages>
 *         			<lang>de-DE</lang>
 *         			<lang>de</lang>
 *         			<lang>en-US</lang>
 *         			<lang>en</lang>
 *         		</languages>
 *         		<messages>
 *         			<received count="1" unread="0"/>
 *         			<sent count="0"/>
 *         		</messages>
 *         	</user>
 */
@Serializable
@XmlSerialName(
    value = "osm"
)
data class UserResponse(
    @SerialName("version")
    val osmVersion: String,
    val generator: String,
    val copyright: String,
    val attribution: String,
    val license: String,
    @XmlSerialName("user")
    val user: OSMUser
)
@Serializable
data class OSMUser (
    val id: Long,

    @SerialName("display_name")
    val displayName: String,

    @SerialName("account_created")
    val accountCreated: String,
    @XmlElement(true)
    val description: String,

    @SerialName("contributor_terms")
    @XmlSerialName("contributor-terms")
    val contributorTerms: ContributorTerms,



//    @XmlSerialName("img")
//    val img: Img,
    @XmlSerialName("changesets")
    val changesets: Changesets,

    @XmlSerialName("traces")
    val traces: Changesets,
    @XmlSerialName("blocks")
    val blocks: Blocks,
//    @XmlSerialName("home")
//    val home: Home,
    @XmlSerialName("languages")
    val languages: List<UserLang>,
    @XmlSerialName("messages")
    val messages: Messages
)

@Serializable
data class UserLang(
    @XmlElement(true)
    val lang: String
)


@Serializable
data class  UserDescription(
    val description: String
)

@Serializable
data class Blocks (
    @XmlSerialName("received")
    val received: BlocksReceived
)

@Serializable
data class BlocksReceived (
    val count: Long,
    val active: Long
)

@Serializable
data class Changesets (
    val count: Long
)

@Serializable
data class ContributorTerms (
    val agreed: Boolean,
    val pd: Boolean
)

@Serializable
data class Home (
    val lat: Double,
    val lon: Double,
    val zoom: Long
)

@Serializable
data class Img (
    val href: String
)

@Serializable
data class Messages (
    @XmlSerialName("received")
    val received: MessagesReceived,
    @XmlSerialName("sent")
    val sent: Changesets
)

@Serializable
data class MessagesReceived (
    val count: Long,
    val unread: Long
)
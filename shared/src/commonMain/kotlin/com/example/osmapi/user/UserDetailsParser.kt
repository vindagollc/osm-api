package com.example.osmapi.user

import com.example.osmapi.core.common.Handler
import com.example.osmapi.core.map.data.OsmLatLon

class UserDetailsParser(private val handler: Handler<UserInfo>) : UserInfoParser(handler) {

    companion object {
        const val USER = "user"
        const val LANGUAGES = "languages"
    }

    private var languages: MutableList<String>? = null

    private var userDetails: UserDetails? = null
    private var text: String? = null

    override fun createUser(id: Long, name: String) {
        userDetails = UserDetails(id, name)
        user = userDetails
    }

    override fun onStartElement(name: String, path: String, attributes: Map<String, String>) {
        super.onStartElement(name, path, attributes)
        val parent = getParent()

        if (LANGUAGES == name) {
            languages = ArrayList()
        } else if (USER == parent) {
            when (name) {
                "contributor-terms" -> {
                    val publicDomain = attributes["pd"]?.toBoolean();
                    if (publicDomain != null)
                        userDetails?.considersHisContributionsAsPublicDomain = publicDomain
                }

                "home" -> {
                    if (attributes["lat"] != null && attributes["lon"] != null) {
                        userDetails?.homeLocation = OsmLatLon.parseLatLon(attributes["lat"]!!, attributes["lon"]!!);
                    }
                    userDetails?.homeZoom = attributes["zoom"]?.toByte()
                }
            }
        } else if (parent == "messages") {
            when (name) {
                "received" -> {
                    userDetails?.inboxMessageCount = attributes["count"]?.toInt() ?: 0
                    userDetails?.unreadMessagesCount = attributes["unread"]?.toInt() ?: 0
                }

                "sent" -> userDetails?.sentMessagesCount = attributes["count"]?.toInt() ?: 0

            }
        }
    }

    override fun onEndElement(name: String, path: String) {
        val parent = getParent()
        if (USER == name) {
            userDetails?.let { handler.handle(it) }
            userDetails = null
            user = null
        } else if (LANGUAGES == name) {
            userDetails?.let { it.preferredLanguages = languages }
            languages = null
        }

        if (LANGUAGES == parent) {
            if ("lang" == name) {
                if (languages != null) {
                    text?.let { languages!!.add(it) }
                }
            }
        }
    }

    override fun onText(text: String) {
        super.onText(text)
        this.text = text
    }
}

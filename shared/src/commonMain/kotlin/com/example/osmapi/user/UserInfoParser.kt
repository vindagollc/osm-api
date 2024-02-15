package com.example.osmapi.user

import com.example.osmapi.core.APIResponseReader
import com.example.osmapi.core.common.Handler
import com.example.osmapi.core.common.XMLParser
import com.example.osmapi.core.user.UserInfo
import kotlinx.datetime.Instant


open class UserInfoParser(private val handler: Handler<UserInfo>) : XMLParser(), APIResponseReader<Unit> {

    companion object {
        const val USER = "user"
        const val ROLES = "roles"
        const val BLOCKS = "blocks"
    }

    private var roles: MutableList<String>? = null
    protected var user: UserInfo? = null
    private var text: String? = null
    override fun parse(xmlString: String) {
        doParse(xmlString)
    }

    open fun createUser(id: Long, name: String) {
        user = UserInfo(id, name)
    }

    override fun onStartElement(name: String, path: String, attributes: Map<String, String>) {
        if (name == USER) {
            createUser(attributes["id"]!!.toLong(), attributes["display_name"]!!)
            user?.createdAt = attributes["account_created"]?.let { Instant.parse(it) }
        }

        if (USER == getParent()) {
            user?.let {
                when (name) {
                    "img" -> it.profileImageUrl = attributes["href"]
                    "changesets" -> it.changesetsCount = attributes["count"]?.toInt() ?: 0
                    "traces" -> it.gpsTracesCount = attributes["count"]?.toInt() ?: 0
                    "contributor-terms" -> it.hasAgreedToContributorTerms = attributes["agreed"]?.toBoolean() == true
                }
            }
        } else if (BLOCKS == getParent()) {
            if (name == "received") {
                user?.isBlocked = attributes["active"]?.toInt() != 0
            }
        }
    }

    override fun onEndElement(name: String, path: String) {
        val parent = getParent()

        if (USER == name) {
            user?.let { handler.handle(it) }
        } else if (ROLES == name) {
            user?.roles = roles
            roles = null
        }

        if (USER == parent) {
            if (name == "description") {
                user?.profileDescription = text
            }
        } else if (ROLES == parent) {
            if (name == "role"){
                if (roles == null){
                    roles = ArrayList(1)
                }
                text?.let { roles!!.add(it) }
            }
        }
    }

    override fun onText(text: String) {
        this.text = text
    }
}
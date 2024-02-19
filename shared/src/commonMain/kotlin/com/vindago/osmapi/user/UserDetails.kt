package com.vindago.osmapi.user

import com.vindago.osmapi.core.map.data.LatLon
import com.vindago.osmapi.core.user.UserInfo


class UserDetails(id: Long, displayName: String) : UserInfo(id, displayName) {
    var considersHisContributionsAsPublicDomain: Boolean = false

    /** user's self-chosen home zoom level is something between 0-19. Null if not set.  */
    var homeZoom: Byte? = null

    /** user's self-chosen home location. Null if not set.  */
    var homeLocation: LatLon? = null

    /** the language and country codes of the user's preferred languages, sorted by
     * preferedness. The format is i.e. "en-US" or "en" (according to ISO 639-1 and ISO 3166)  */
    var preferredLanguages: List<String>? = null

    var inboxMessageCount: Int = 0
    var unreadMessagesCount: Int = 0
    var sentMessagesCount: Int = 0
}
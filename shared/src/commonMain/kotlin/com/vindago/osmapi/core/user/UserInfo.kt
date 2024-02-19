package com.vindago.osmapi.core.user

import kotlinx.datetime.Instant

open class UserInfo(id: Long, displayName: String) : User(id, displayName) {

    var createdAt: Instant? = null

    /** aka the number of edits  */
    var changesetsCount: Int = 0
    var gpsTracesCount: Int = 0

    /** URL to the user's profile picture. May be null if no profile picture has been chosen.  */
    var profileImageUrl: String? = null

    /** The profile description is formatted with markdown. May be null if no description was
     * provided.  */
    var profileDescription: String? = null

    var hasAgreedToContributorTerms: Boolean = false

    /** whether the user is currently blocked (=cannot make any modifications on the map).  */
    var isBlocked: Boolean = false

    /** may be null if the user has no roles  */
    var roles: List<String>? = null

    /** whether this user holds the given role. See UserRole for constants for known roles
     * and the methods isModerator and isAdministrator  */
    private fun hasRole(roleName: String): Boolean {
        return roles != null && roles!!.contains(roleName)
    }

    fun isModerator(): Boolean {
        return hasRole(UserRole.MODERATOR)
    }

    fun isAdministrator(): Boolean {
        return hasRole(UserRole.ADMINISTRATOR)
    }
}

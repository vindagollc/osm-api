package com.vindago.osmapi.user

import com.vindago.osmapi.core.OSMConnection
import com.vindago.osmapi.core.common.ListHandler
import com.vindago.osmapi.core.common.SingleElementHandler
import com.vindago.osmapi.core.common.errors.OsmNotFoundException
import com.vindago.osmapi.core.user.UserInfo


/**
 * Get user infos and details
 * All the interactions within this class require a connection with logged-in user.
 */
class UserApi(val osm: OSMConnection) {

    /**
     * Returns the current userinfo
     */
    suspend fun getMine(): UserInfo {
        val handler: SingleElementHandler<UserInfo> = SingleElementHandler()
        osm.fetchAuthenticated("user/details", UserDetailsParser(handler))
        return handler.get() as UserDetails
    }

    /**
     * @param userId id of the user to get the user info for
     * @return the user info of the given user. Null if the user does not exist.
     *  */
    suspend fun get(userId: Long): UserInfo? {
        return try {
            val handler = SingleElementHandler<UserInfo>()
            osm.fetchAuthenticated("user/$userId", UserInfoParser(handler))
            handler.get()!!
        } catch (e: OsmNotFoundException) {
            null
        }
    }

    /**
     * @param userIds ids of users to get the use info for
     * @return list of user info
     */
    suspend fun getAll(userIds: Collection<Long?>): List<UserInfo?> {
        if (userIds.isEmpty()) return emptyList()
        val handler = ListHandler<UserInfo>()
        osm.fetchAuthenticated("users?users=" + toCommaList(userIds), UserInfoParser(handler))
        return handler.get()
    }

    /**
     * @param ids ids of users to get the use info for
     * @return string of ids separated by a comma
     */
    private fun toCommaList(ids: Iterable<Long?>): String {
        val result = StringBuilder()
        var first = true
        for (id in ids) {
            if (id == null) continue

            if (first) first = false
            else result.append(",")
            result.append(id)
        }
        return result.toString()
    }
}
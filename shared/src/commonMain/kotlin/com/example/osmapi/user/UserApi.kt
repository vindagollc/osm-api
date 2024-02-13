package com.example.osmapi.user

import com.example.osmapi.core.OSMConnection
import com.example.osmapi.core.common.ListHandler
import com.example.osmapi.core.common.SingleElementHandler
import com.example.osmapi.core.common.errors.OsmNotFoundException
import com.example.osmapi.core.user.UserInfo


/**
 * Get user infos and details
 * All the interactions within this class require a connection with logged in user.
 */
class UserApi(val osm: OSMConnection) {

    suspend fun getMine(): UserInfo {
        val handler: SingleElementHandler<UserInfo> = SingleElementHandler()
        val info = UserInfo(1, "Hi")
        //osm.fetchAuthenticated<UserInfo>("user/details",)
        osm.fetchAuthenticated("user/details", UserDetailsParser(handler))
        return handler.get() as UserDetails
    }

    suspend fun get(userId: Long): UserInfo? {
        return try {
            val handler = SingleElementHandler<UserInfo>()
            osm.fetchAuthenticated("user/$userId", UserInfoParser(handler))
            handler.get()!!
        } catch (e: OsmNotFoundException) {
            null
        }
    }

    suspend fun getAll(userIds: Collection<Long?>): List<UserInfo> {
        if (userIds.isEmpty()) return emptyList()
        val handler = ListHandler<UserInfo>()
        osm.fetchAuthenticated("users?users=" + toCommaList(userIds), UserInfoParser(handler))
        return handler.get()
    }

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
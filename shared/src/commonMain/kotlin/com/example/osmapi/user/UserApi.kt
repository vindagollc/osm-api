package com.example.osmapi.user

import com.example.osmapi.core.OSMConnection
import com.example.osmapi.core.common.SingleElementHandler
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
}
package com.example.osmapi.core.user

import com.example.osmapi.core.OSMConnection

/** Get the user permissions */
class PermissionsApi(val osm: OSMConnection) {

    /** @return a list of permissions the user has on this server (=are granted though OAuth). Use
     *          the constants defined in the Permission, i.e Permission.CHANGE_PREFERENCES */
    suspend  fun get():List<String> {
        return osm.fetchAuthenticated("permissions", PermissionsParser())
    }
}
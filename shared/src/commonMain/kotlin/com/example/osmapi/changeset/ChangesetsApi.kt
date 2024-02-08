package com.example.osmapi.changeset

import com.example.osmapi.core.OSMConnection
import com.example.osmapi.core.common.SingleElementHandler


/**
 * Gets information for, searches for and shows changeset discussions
 * All interactions with this class require an osm connection with a logged in user.
 */
class ChangesetsApi(val osm: OSMConnection) {

    companion object
    {
        const val CHANGESET = "changeset"
    }

    /**
     * Fetches a single changeset
     */
   suspend fun get(id: Long) : ChangesetInfo {
        val query = "$CHANGESET/$id?include_discussion=true"
        val handler: SingleElementHandler<ChangesetInfo> = SingleElementHandler()
        osm.fetchAuthenticated(query, ChangesetParser(handler))
        return handler.get()!!
    }

    /**
     * Adds a comment to the changeset
     */
    fun comment(id:Long, text: String= ""){

    }

    private  fun urlEncodeText(text:String){
        // TODO: Encode to URL
    }

    fun getData(id: Long){

    }
}
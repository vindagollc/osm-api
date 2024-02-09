package com.example.osmapi.changeset

import com.example.osmapi.core.OSMConnection
import com.example.osmapi.core.common.SingleElementHandler
import io.ktor.http.encodeURLParameter


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
    suspend  fun comment(id:Long, text: String= "") : ChangesetInfo {
        if(text.isEmpty()){
            throw  IllegalArgumentException("Text must not be empty")
        }
        val handler: SingleElementHandler<ChangesetInfo> = SingleElementHandler()
        val apiCall = "$CHANGESET/$id" + "/comment?text=" + urlEncodeText(text)
        osm.post(apiCall,ChangesetParser(handler))
        return handler.get()!!
    }

    private  fun urlEncodeText(text:String): String {
        //TODO: Need to verify this
        return     text.encodeURLParameter()

    }

    fun getData(id: Long){

    }
}
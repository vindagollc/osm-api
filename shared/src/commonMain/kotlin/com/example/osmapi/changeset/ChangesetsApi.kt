package com.example.osmapi.changeset

import com.example.osmapi.core.OSMConnection
import com.example.osmapi.core.common.Handler
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

    suspend fun find(handler:Handler<ChangesetInfo>, filters: QueryChangesetFilters? = null) {
        val paramString : String = filters?.toParamString() ?: ""
        val query = if(paramString.isEmpty()) "" else "?$paramString"
        val path = CHANGESET+"s"+query
        osm.fetchAuthenticated(path,ChangesetParser(handler)) // Handler will get the data.
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

    suspend fun subscribe(id:Long) : ChangesetInfo {
        val handler: SingleElementHandler<ChangesetInfo> = SingleElementHandler()
        val apiPath = "$CHANGESET/$id/subscribe"
        osm.post(apiPath,ChangesetParser(handler))
        return handler.get()!!
    }

    private  fun urlEncodeText(text:String): String {
        return     text.encodeURLParameter()
    }



    fun getData(id: Long){

    }
}
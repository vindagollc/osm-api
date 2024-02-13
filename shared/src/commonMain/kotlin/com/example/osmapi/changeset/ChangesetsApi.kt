package com.example.osmapi.changeset

import com.example.osmapi.core.OSMConnection
import com.example.osmapi.core.common.Handler
import com.example.osmapi.core.common.SingleElementHandler
import com.example.osmapi.core.common.errors.OsmConflictException
import com.example.osmapi.core.common.errors.OsmNotFoundException
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
     *  Get the changeset information with the given id. Always includes the changeset discussion.
     *  @param id changeset id
     *  @return info for the given changeset. Null if there is no changeset
     */
   suspend fun get(id: Long) : ChangesetInfo? {
        val query = "$CHANGESET/$id?include_discussion=true"
        val handler: SingleElementHandler<ChangesetInfo> = SingleElementHandler()
        return try {
            osm.fetchAuthenticated(query, ChangesetParser(handler))
            handler.get()
        } catch (e : OsmNotFoundException){
            null
        }
    }

    /**
     * Get a number of changesets that match the given filters.
     * @param handler The handler which is fed the incoming changeset infos
     * @param filters what to search for .ie,
     *          new QueryChangesetsFilters().byUser(123).onlyClosed()
     * @throws  OsmAuthorizationException if not logged in
     */
    suspend fun find(handler:Handler<ChangesetInfo>, filters: QueryChangesetFilters? = null) {
        val paramString : String = filters?.toParamString() ?: ""
        val query = if(paramString.isEmpty()) "" else "?$paramString"
        val path = CHANGESET+"s"+query
        try {
            osm.fetchAuthenticated(path, ChangesetParser(handler)) // Handler will get the data.
        } catch (e: OsmNotFoundException){
//            throw e  // Ignore this
        }

    }

    /**
     *  Add a comment to the given changeset. The changeset must already be closed. Adding a comment
     *  to a changeset automatically subscribes the user to it.
     *  @param id id of the changeset
     *  @param text text to add to the changeset: Must not be empty
     *  @return the updated changeset
     *  @throws OsmAuthorizationException if this application is not authorized to modify the map
     *                          (Permission.MODIFY_MAP)
     *  @throws  OsmConflictException if the changeset is not yet closed. (Only closed changesets can be commented)
     *
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

    /**
     * Subscribe the user to a changeset discussion. The changeset must be closed already.
     * @param id id of the changeset
     * @return the changesetInfo
     * @throws OsmAuthorizationException if this application is not authorized to modify the map
     *                             (Permission.MODIFY_MAP)
     * @throws OsmNotFoundException if the given changeset does not exist
     */
    suspend fun subscribe(id:Long) : ChangesetInfo? {
        val handler: SingleElementHandler<ChangesetInfo> = SingleElementHandler()
        val apiPath = "$CHANGESET/$id/subscribe"
        return try {
            osm.post(apiPath, ChangesetParser(handler))
            handler.get()!!
        } catch (ignore: OsmConflictException){
            //Ignoring the exception when user already subscribed to the changeset
            get(id)
        }

    }

    suspend fun unsubscribe(id: Long): ChangesetInfo {
        val handler: SingleElementHandler<ChangesetInfo> = SingleElementHandler()
        val apiPath = "$CHANGESET/$id/unsubscribe"
         try {
            osm.post(apiPath, ChangesetParser(handler))
             return handler.get()!!
        } catch (ignore: OsmNotFoundException){
            //TODO: Need to make it better.
            val result = get(id)
           throw  ignore //
        }
    }

    private  fun urlEncodeText(text:String): String {
        return     text.encodeURLParameter()
    }



    fun getData(id: Long){

    }
}
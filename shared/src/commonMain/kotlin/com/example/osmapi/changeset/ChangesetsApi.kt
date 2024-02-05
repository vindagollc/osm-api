package com.example.osmapi.changeset


/**
 * Gets information for, searches for and shows changeset discussions
 * All interactions with this class require an osm connection with a logged in user.
 */
class ChangesetsApi {

    companion object
    {
        const val CHANGESET = "changeset"
    }

    /**
     * Fetches a single changeset
     */
    fun get(id: Long){

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
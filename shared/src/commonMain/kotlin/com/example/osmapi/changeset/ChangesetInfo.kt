package com.example.osmapi.changeset

import com.example.osmapi.core.changesets.Changeset
import com.example.osmapi.core.map.data.BoundingBox
import com.example.osmapi.core.user.User
import kotlinx.datetime.Instant

/** Includes information for a changeset inclusive the changeset discussion but exclusive the
 *  elements that were changed in the changeset.
 */
class ChangesetInfo(id: Long, user: User) : Changeset(id, user) {
    /** map of tags associated with this changeset. May be empty if there are no tags at all. */
    var tags: MutableMap<String, String> = mutableMapOf()
    /** the number of notes in the changeset discussion of this changeset */
    var noteCount: Int = 0
    /** the number of changes in a changeset */
    var changesCount: Int = 0
    var boundingBox: BoundingBox = BoundingBox(0.0,0.0,0.0,0.0) // TODO: Make a default one
    var isOpen: Boolean = false
    /** The changeset discussion. May be empty if there is none */
    var discussion: List<ChangesetNote> = mutableListOf()
    /** the date the changeset was created. Default date  if the changeset is still open  */
    var createdAt: Instant = Instant.fromEpochMilliseconds(0)  // Modify later
    /** the date the changeset was closed. Default date if the changeset is still open  */
    var closedAt: Instant = Instant.fromEpochMilliseconds(0)

    /** A shortcut to getTags().get("comment")
     * @return the "commit comment" of the changeset which should include information about what was
     *		 changed (and why). null if none supplied.
     * */
    fun getChangesetComment(): String? {
        return tags["comment"]
    }

    /** A shortcut to getTags().get("source").split(";")
     * @return the source of the data entered. Common values include "bing", "survey", "local
     *         knowledge", "common knowledge", "extrapolation", "photograph" (and more). null if
     *         none supplied.
     * */
    fun getSources() : List<String> {
        val sources = tags["source"]
        if (sources == null){return emptyList() }
        return sources!!.split("(\\s)?;(\\s)?") //TODO: Figure this method out
    }

    /** A shortcut to getTags().get("created_by")
     * @return the application with which this changeset has been created. null if none supplied. */
    fun getGenerator() : String? {
        return tags["created_by"]
    }

}
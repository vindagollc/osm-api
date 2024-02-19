package com.vindago.osmapi.changeset

import com.vindago.osmapi.core.map.data.BoundingBox
import io.ktor.http.encodeURLParameter
import io.ktor.http.encodeURLQueryComponent
import kotlinx.datetime.Instant


class QueryChangesetFilters {

    private var params: MutableMap<String, String> = mutableMapOf()

    /**
     * @param userName limit search to only the given user name
     *
     * @return this
     *
     * @throws IllegalArgumentException if a user has already been specified by id
     */
    fun byUser(userName: String): QueryChangesetFilters {
        if (params.containsKey("user")) {
            throw IllegalArgumentException("already provided a user ID")
        }
        params["display_name"] = userName.encodeURLParameter()
        return this
    }

    /**
     * @param userId limit search to only the given user id
     *
     * @return this
     *
     * @throws IllegalArgumentException if a user has already been specified by user name
     */
    fun byUser(userId: Long): QueryChangesetFilters {
        if (params.containsKey("display_name")) {
            throw IllegalArgumentException("already provided a user name")
        }
        params["user"] = userId.toString()
        return this
    }

    /**
     * @param bounds limit search by these bounds
     *
     * @return this
     *
     * @throws IllegalArgumentException if the bounds do cross the 180th meridian
     */
    fun byBounds(bounds: BoundingBox): QueryChangesetFilters {

        params["bbox"] = bounds.getAsLeftBottomRightTopString()
        return this
    }

    /**
     * Return only open changesets
     *
     * @return this
     */

    fun onlyClosed(): QueryChangesetFilters {
        params["closed"] = "true"
        return this
    }

    /**
     * Return only closed changesets
     *
     * @return this
     */
    fun onlyOpen(): QueryChangesetFilters {
        params["open"] = "true"
        return this
    }

    /** @param changesetIds at least one changeset id to search for
     *
     *  @return this
     *
     *  @throws IllegalArgumentException if the collection is empty  */

    fun byChangesets(vararg changesetIds: Long): QueryChangesetFilters {
        if (changesetIds.isEmpty()) {
            throw IllegalArgumentException("Must at least supply one changeset id")
        }
        val stringValue = changesetIds.joinToString(",")
        params["changesets"] = stringValue
        return this
    }

    /** @param changesets at least one changeset id to search for
     *  @return this
     *  @throws IllegalArgumentException if the collection is empty  */
    fun byChangesets(changesets: List<Long>): QueryChangesetFilters {

        if (changesets.isEmpty()) {
            throw IllegalArgumentException("Must at least supply one changeset id")
        }
        val stringValue = changesets.joinToString(",")
        params["changesets"] = stringValue
        return this
    }

    /**
     * @param closedAfter limit search to changesets that have been closed after this instant
     *
     * @return this
     */
    fun byClosedAfter(closedAfter: Instant): QueryChangesetFilters {
        params["time"] = closedAfter.toString()
        return this
    }

    /**
     * Filter by changesets that have at one time been open during the given time range
     *
     * @param closedAfter limit search to changesets that have been closed after this instant
     * @param createdBefore limit search to changesets that have been created before this instant
     *
     * @return this
     */
    fun byOpenSomeTimeBetween(createdBefore: Instant, closedAfter: Instant): QueryChangesetFilters {
        params["time"] = "$closedAfter,$createdBefore"
        return this
    }

    /**
     * @param count return at most this many changesets. The default can be looked up with the capabilities api call.
     *              In 2023-11, the default was 100 and the maximum query limit was 100, too.
     */
    fun limit(count: Int): QueryChangesetFilters {
        if (count <= 0) {
            throw IllegalArgumentException("limit must be positive")
        }
        params["limit"] = count.toString()
        return this
    }

    fun toParamString(): String {
        return params.map { it.key.encodeURLParameter() + "=" + it.value.encodeURLQueryComponent() }
            .joinToString("&")
    }
}
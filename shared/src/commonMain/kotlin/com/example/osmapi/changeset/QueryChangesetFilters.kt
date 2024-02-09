package com.example.osmapi.changeset

import com.example.osmapi.core.map.data.BoundingBox
import io.ktor.http.encodeURLParameter
import io.ktor.http.encodeURLQueryComponent
import kotlinx.datetime.Instant



class QueryChangesetFilters {

    private var params: MutableMap<String, String> = mutableMapOf()

    fun byUser(userName:String): QueryChangesetFilters {
        if (params.containsKey("user")) {
            throw  IllegalArgumentException("already provided a user ID")
        }
        params["display_name"] = userName.encodeURLParameter()
        return this
    }

    fun byUser(userId: Long) : QueryChangesetFilters{
        if(params.containsKey("display_name")) {
            throw  IllegalArgumentException("already provided a user name")
        }
        params["user"] = userId.toString()
        return  this
    }

    fun byBounds(bounds: BoundingBox) : QueryChangesetFilters {

        params["bbox"] = bounds.getAsLeftBottomRightTopString()
        return this
    }

    fun onlyClosed() : QueryChangesetFilters {
        params["closed"] = "true"
        return this
    }
    fun onlyOpen(): QueryChangesetFilters {
        params["open"] = "true"
        return this
    }

    fun byChangesets(vararg  changesetIds: Long) : QueryChangesetFilters {
        if(changesetIds.isEmpty()){
            throw  IllegalArgumentException("Must at least supply one changeset id")
        }
        val stringValue = changesetIds.joinToString("," )
        params["changesets"] = stringValue
        return this
    }

    fun byChangesets(changesets:List<Long>) : QueryChangesetFilters {
        
        if(changesets.isEmpty()){
            throw  IllegalArgumentException("Must at least supply one changeset id")
        }
        val stringValue = changesets.joinToString("," )
        params["changesets"] = stringValue
        return this
    }
    fun byClosedAfter(closedAfter: Instant): QueryChangesetFilters {
        params["time"] = closedAfter.toString()
        return this
    }

    fun byOpenSomeTimeBetween(createdBefore:Instant,closedAfter: Instant): QueryChangesetFilters {
        params["time"] = "$closedAfter,$createdBefore"
        return this
    }

    fun limit(count: Int) : QueryChangesetFilters {
        if (count <= 0){
            throw  IllegalArgumentException("limit must be positive")
        }
        params["limit"] = count.toString()
        return this
    }

    fun toParamString() : String {
        return params.map { it.key.encodeURLParameter()+"="+it.value.encodeURLQueryComponent() }.joinToString("&")
    }
}
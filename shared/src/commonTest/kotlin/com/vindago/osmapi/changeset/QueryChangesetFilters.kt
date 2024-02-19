package com.vindago.osmapi.changeset

import com.vindago.osmapi.core.map.data.BoundingBox
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail


class QueryChangesetFiltersTests {
    @Test
    fun byBounds(){
        val filters = QueryChangesetFilters()
        filters.byBounds(BoundingBox(0.0,5.0,10.0,15.0))
        assertEquals("5.0,0.0,15.0,10.0",getParam(filters.toParamString(),"bbox"))

    }

    @Test
    fun byChangesets(){
        val filters = QueryChangesetFilters()
        filters.byChangesets(1,2,3)
        assertEquals("1,2,3",getParam(filters.toParamString(),"changesets"))
    }

    @Test
    fun byUserId(){
        val filters = QueryChangesetFilters()
        filters.byUser(4)
        assertEquals("4",getParam(filters.toParamString(),"user"))
    }

    @Test
    fun onlyClosed(){
        val filters = QueryChangesetFilters()
        filters.onlyClosed()
        assertEquals("true",getParam(filters.toParamString(),"closed"))
    }

    @Test
    fun onlyOpen(){
        val filters = QueryChangesetFilters()
        filters.onlyOpen()
        assertEquals("true",getParam(filters.toParamString(),"open"))
    }

    @Test
    fun byClosedAfter(){
        val filters = QueryChangesetFilters()
        val validInstant: Instant = Instant.parse("2222-11-22T22:11:00Z")
        filters.byClosedAfter(validInstant)

        assertEquals(validInstant,Instant.parse(getParam(filters.toParamString(),"time")!!))
    }

    @Test
    fun twoDates() {
        val filters = QueryChangesetFilters()
        filters.byOpenSomeTimeBetween(Clock.System.now(),Clock.System.now())
        val filterString = filters.toParamString()
        assertTrue { getParam(filterString,"time")!!.contains(",") }
    }

    @Test
    fun byChangesetIds(){
        val filters = QueryChangesetFilters()
        filters.byChangesets(1,2,3)
        assertEquals("1,2,3", getParam(filters.toParamString(),"changesets"))
    }

    @Test
    fun byUserName(){
        val filters = QueryChangesetFilters()
        filters.byUser("hans")
        assertEquals("hans",getParam(filters.toParamString(),"display_name"))

    }

    @Test
    fun illegalBounds(){
        val filters = QueryChangesetFilters()
        filters.byBounds(BoundingBox(0.0,15.0,10.0,5.0))
        // not added illegal bounds
    }

    @Test
    fun illegalUserSelection() {
        val filters = QueryChangesetFilters()
        try {
            filters.byUser(1)
            filters.byUser("hans")
            fail();
        } catch (e:IllegalArgumentException){}
        val filters2 = QueryChangesetFilters()
        try {
            filters2.byUser("hans")
            filters2.byUser(1)
            fail();
        }catch (e:IllegalArgumentException){}
    }

    @Test
    fun illegalChangesetCount() {
        try{
            QueryChangesetFilters().byChangesets()
            fail()
        }catch (e:IllegalArgumentException){}


    }

    @Test
    fun limit(){
        val filters = QueryChangesetFilters()
        filters.limit(10)
        assertEquals("10",getParam(filters.toParamString(),"limit"))
    }

    @Test
    fun illegalLimit(){
        val filters = QueryChangesetFilters()
        try {
            filters.limit(0)
            fail()
        }catch (e:IllegalArgumentException){}

    }

    @Test
    fun testInclusion(){
        // Dummy test for some join
        val listOfStrings : List<String> = listOf("a")
        val joined = listOfStrings.joinToString(",")
        print(joined)
    }
    private fun getParam(params: String, paramName: String): String? {
        val startsAt = params.indexOf(paramName)
        if (startsAt == -1) {
            throw RuntimeException("Param name not found")
        }
        val valueStartsAt = startsAt + paramName.length + 1
        val valueEndsAt = params.indexOf("&", valueStartsAt)
        return if (valueEndsAt == -1) params.substring(valueStartsAt) else params.substring(
            valueStartsAt,
            valueEndsAt
        )
    }
}
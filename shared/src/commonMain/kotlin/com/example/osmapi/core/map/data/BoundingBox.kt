package com.example.osmapi.core.map.data

class BoundingBox(private  val min: OsmLatLon, private  val max: OsmLatLon) {

    constructor(latmin: Double, lonMin:Double, latMax:Double, lonMax:Double): this(OsmLatLon(lonMin,latmin),OsmLatLon(lonMax,latMax))

    fun getMin(): LatLon{
        return min
    }
    fun getMax(): LatLon{
        return max
    }
}
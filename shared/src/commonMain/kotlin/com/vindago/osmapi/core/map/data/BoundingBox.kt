package com.vindago.osmapi.core.map.data

class BoundingBox(private  val min: OsmLatLon, private  val max: OsmLatLon) {

    constructor(latmin: Double, lonMin:Double, latMax:Double, lonMax:Double): this(OsmLatLon(lonMin,latmin),OsmLatLon(lonMax,latMax))

    fun getMin(): LatLon{
        return min
    }
    fun getMax(): LatLon{
        return max
    }
    fun getMaxLat(): Double{
        return max.getLatitude()
    }
    fun getMaxLon(): Double{
        return max.getLongitude()
    }
    fun getMinLon(): Double {
        return min.getLongitude()
    }
    fun getMinLat(): Double {
        return min.getLatitude()
    }

    fun getAsLeftBottomRightTopString() : String {
        return "${getMinLon()},${getMinLat()},${getMaxLon()},${getMaxLat()}"
    }
}
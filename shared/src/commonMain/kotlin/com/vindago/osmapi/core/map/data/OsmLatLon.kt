package com.vindago.osmapi.core.map.data

class OsmLatLon(private val longitude: Double, private val latitude: Double) : LatLon {
    override fun getLatitude(): Double {
        return latitude
    }

    override fun getLongitude(): Double {
        return longitude
    }

    constructor(other:LatLon) : this(other.getLongitude(),other.getLatitude())

    companion object{
        fun parseLatLon(lat: String, lon: String): OsmLatLon {
            return OsmLatLon(lat.toDouble(), lon.toDouble())
        }
        val min = object: LatLon{
            override fun getLatitude(): Double {
                return -90.0
            }

            override fun getLongitude(): Double {
                return -180.0
            }
        }
        val max = object : LatLon{
            override fun getLatitude(): Double {
                return 90.0
            }

            override fun getLongitude(): Double {
                return 180.0
            }
        }

        fun checkValidity(lat:Double, lon: Double){
            if(! isValid(lat,lon)){
                throw IllegalArgumentException("Not a valid position")
            }
        }
        fun checkValidity(x:LatLon){
            checkValidity(x.getLatitude(),x.getLongitude())
        }
        fun isValid(x: LatLon): Boolean{
            return isValid(x.getLatitude(),x.getLongitude())
        }
        fun isValid(lat:Double, lon:Double): Boolean{
            return lon >= min.getLongitude()
                    && lon <= max.getLongitude()
                    && lat >= min.getLatitude()
                    && lat <= max.getLatitude()
        }
    }


}
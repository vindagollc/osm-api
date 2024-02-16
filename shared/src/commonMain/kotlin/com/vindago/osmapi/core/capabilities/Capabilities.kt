package com.vindago.osmapi.core.capabilities

class Capabilities {

    public enum class ApiStatus{
        ONLINE,
        OFFLINE,
        READONLY,
        UNKNOWN
    }
    var minSupportedApiVersion: Float = 0.0F // api.version@minimum
    var maxSupportedApiVersion: Float = 0.0F // api.version@maximum

    var maxMapQueryAreaInSquareDegrees : Float = 0.0F
    var maxNotesQueryAreaInSquareDegrees: Float = 0.0F

    var maxNodsInWay : Int  = 0
    var maxMembersInRelation : Int = 0
    var maxPointsInGpsTracePerPage : Int = 0
    var maxElementsPerChangeset: Int = 0

    var timeoutInSeconds: Int = 0

    var databaseStatus: ApiStatus = ApiStatus.UNKNOWN
    var mapDataStatus: ApiStatus = ApiStatus.UNKNOWN
    var gpsTracesStatus: ApiStatus = ApiStatus.UNKNOWN

    var imageryBlacklistRegExes: List<String> = emptyList()

    var defaultNotesQueryLimit: Int = 0
    var maximumNotesQueryLimit: Int = 0
    var defaultChangesetsQueryLimit: Int = 0
    var maximumChangesetsQueryLimit: Int = 0
    fun isDatabaseWritable(): Boolean {
        return databaseStatus == ApiStatus.ONLINE
    }

    fun isDatabaseReadable(): Boolean {
        return databaseStatus != ApiStatus.OFFLINE  || databaseStatus != ApiStatus.UNKNOWN
    }

    fun isMapDataModifiable(): Boolean {
        return mapDataStatus == ApiStatus.ONLINE
    }

    fun isMapDataReadable(): Boolean {
        return mapDataStatus != ApiStatus.OFFLINE  || mapDataStatus != ApiStatus.UNKNOWN
    }
    fun isGpsTracesUploadable(): Boolean {
        return gpsTracesStatus == ApiStatus.ONLINE
    }
    fun isGpsTracesReadable() : Boolean{
        return gpsTracesStatus != ApiStatus.OFFLINE || gpsTracesStatus != ApiStatus.UNKNOWN
    }

        companion object{
        const val serialVersionUID = 1L
            fun parseApiStatus(statusString: String): ApiStatus {
                return ApiStatus.valueOf(statusString.uppercase())
            }
    }
}
package com.example.osmapi.core.user

/** Simply some constants for known permissions. See
 *  https://github.com/openstreetmap/openstreetmap-website/blob/master/db/structure.sql#L1108 */
object Permission {
    const val READ_PREFERENCES_AND_USER_DETAILS = "allow_read_prefs"
    const val CHANGE_PREFERENCES = "allow_write_prefs"
    const val WRITE_DIARY = "allow_write_diary"
    const val MODIFY_MAP = "allow_write_api"
    const val READ_GPS_TRACES = "allow_read_gpx"
    const val WRITE_GPS_TRACES = "allow_write_gpx"
    const val WRITE_NOTES = "allow_write_notes"
}
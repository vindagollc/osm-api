import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlSerialName


enum class ApiStatus{
    ONLINE,
    OFFLINE,
    READONLY,
    UNKNOWN
}

@Serializable
@XmlSerialName(
    value = "osm"
)
data class Capabilities(
    @SerialName("version")
    val osmVersion: String,
    val generator: String,
    val copyright: String,
    val attribution: String,
    val license: String,
    @XmlSerialName("api")
    val api: Api,
    @XmlSerialName("policy")
    val policy: Policy,
){
    fun isMapDataReadable(): Boolean {
        return parseApiStatus(api.status.api) != ApiStatus.OFFLINE  ||
                parseApiStatus(api.status.api) != ApiStatus.UNKNOWN
    }

    fun isDatabaseWritable(): Boolean {
        return parseApiStatus(api.status.database) == ApiStatus.ONLINE
    }

    fun isDatabaseReadable(): Boolean {
        return parseApiStatus(api.status.database) != ApiStatus.OFFLINE  ||
                parseApiStatus(api.status.database) != ApiStatus.UNKNOWN
    }

    fun isMapDataModifiable(): Boolean {
        return parseApiStatus(api.status.api) == ApiStatus.ONLINE
    }

    fun isGpsTracesUploadable(): Boolean {
        return parseApiStatus(api.status.gpx) == ApiStatus.ONLINE
    }
    fun isGpsTracesReadable() : Boolean{
        return parseApiStatus(api.status.gpx) != ApiStatus.OFFLINE ||
                parseApiStatus(api.status.gpx) != ApiStatus.UNKNOWN
    }

    private fun parseApiStatus(statusString: String): ApiStatus {
        return ApiStatus.valueOf(statusString.uppercase())
    }
}



@Serializable
data class Api(
    val version: Version,
    val area: Area,
    val note_area: NoteArea,
    val tracepoints: Tracepoints,
    val waynodes: Waynodes,
    val relationmembers: Relationmembers,
    val changesets: Changesets,
    val notes: Notes,
    val timeout: Timeout,
    val status: Status
)

@Serializable
@XmlSerialName(
    value = "version"
)
data class Version(
    val minimum: String,
    val maximum: String
)

@Serializable
@XmlSerialName(
    value = "area"
)
data class Area(
    val maximum: Double
)

@Serializable
@XmlSerialName(
    value = "note_area"
)
data class NoteArea(
    val maximum: Int
)

@Serializable
@XmlSerialName(
    value = "tracepoints"
)
data class Tracepoints(
    @SerialName("per_page")
    val perPage: Int
)

@Serializable
@XmlSerialName(
    value = "waynodes"
)
data class Waynodes(
    val maximum: Int
)

@Serializable
@XmlSerialName(
    value = "relationmembers"
)
data class Relationmembers(
    val maximum: Int
)

@Serializable
@XmlSerialName(
    value = "changesets"
)
data class Changesets(
    @SerialName("maximum_elements")
    val maximumElements: Int,
    @SerialName("default_query_limit")
    val defaultQueryLimit: Int,
    @SerialName("maximum_query_limit")
    val maximumQueryLimit: Int
)

@Serializable
@XmlSerialName(
    value = "notes"
)
data class Notes(
    @SerialName("default_query_limit")
    val defaultQueryLimit: Int,
    @SerialName("maximum_query_limit")
    val maximumQueryLimit: Int
)

@Serializable
@XmlSerialName(
    value = "timeout"
)
data class Timeout(
    val seconds: Int
)

@Serializable
@XmlSerialName(
    value = "status"
)
data class Status(
    val database: String = ApiStatus.UNKNOWN.name,
    val api: String = ApiStatus.UNKNOWN.name,
    val gpx: String = ApiStatus.UNKNOWN.name
)

@Serializable
data class Policy(
    val imagery: Imagery
)

@Serializable
@XmlSerialName(
    value = "imagery"
)
data class Imagery(
    val blacklist: List<Blacklist>
)

@Serializable
@XmlSerialName(
    value = "blacklist"
)
data class Blacklist(
    val regex: String
)

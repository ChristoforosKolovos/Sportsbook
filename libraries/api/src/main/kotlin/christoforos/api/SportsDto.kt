package christoforos.api

import com.google.gson.annotations.SerializedName

data class SportsDto(
    @SerializedName("i")
    val id: String?,
    @SerializedName("d")
    val description: String?,
    @SerializedName("e")
    val events: List<EventRaw>?
) {

    inner class EventRaw(
        @SerializedName("i")
        val id: String?,
        @SerializedName("d")
        val description: String?,
        @SerializedName("si")
        val sportId: String?,
        @SerializedName("sh")
        val header: String?,
        @SerializedName("tt")
        val timestamp: String?
    )

}
package christoforos.common.domain.models.event

data class Event(
    val id: String?,
    val description: String?,
    val sportId: String?,
    val header: String?,
    val timestamp: String?,
    var favorite: Boolean = false
)

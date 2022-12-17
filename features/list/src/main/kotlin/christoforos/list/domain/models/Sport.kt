package christoforos.list.domain.models

data class Sport(
    val id: String?,
    val description: String?,
    var events: List<Event>?,
    var collapsed: Boolean = false
)
package christoforos.list.domain.models

import christoforos.common.domain.models.event.Event

data class Sport(
    val id: String?,
    val description: String?,
    var events: List<Event>?,
    var collapsed: Boolean = false
)
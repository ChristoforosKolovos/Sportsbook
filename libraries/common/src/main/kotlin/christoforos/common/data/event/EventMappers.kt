package christoforos.common.data.event

import christoforos.common.domain.models.event.Event
import christoforos.common.utils.StringConstants.EMPTY
import christoforos.database.event.EventEntity

object EventMappers {

    fun Event.toEventEntity() = EventEntity(
        id = id ?: EMPTY,
        description = description,
        sportId = sportId,
        header = header,
        timestamp = timestamp,
        favorite = favorite
    )

    fun List<EventEntity>.toEvents() = this.map { it.toEvent() }

    private fun EventEntity.toEvent() = Event(
        id = id ?: EMPTY,
        description = description,
        sportId = sportId,
        header = header,
        timestamp = timestamp,
        favorite = favorite
    )
}
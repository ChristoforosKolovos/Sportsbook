package christoforos.list.data.event

import christoforos.common.utils.StringConstants.EMPTY
import christoforos.database.event.EventEntity
import christoforos.list.domain.models.Event

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
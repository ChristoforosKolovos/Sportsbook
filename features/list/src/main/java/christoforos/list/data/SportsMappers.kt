package christoforos.list.data

import christoforos.api.SportsDto
import christoforos.list.domain.models.Event
import christoforos.list.domain.models.Sport

object SportsMappers {

    fun List<SportsDto>.toSports() = this.map { it.toSport() }

    private fun SportsDto.toSport() = Sport(
        id = this.id,
        description = this.description,
        events = this.events?.map { it.toEvent() } ?: listOf()
    )

    private fun SportsDto.EventRaw.toEvent() = Event(
        id = this.id,
        description = this.description,
        sportId = this.sportId,
        header = this.header,
        timestamp = this.timestamp
    )

}
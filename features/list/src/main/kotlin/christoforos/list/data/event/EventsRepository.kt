package christoforos.list.data.event

import christoforos.common.domain.models.outcome.Outcome
import christoforos.list.domain.models.Event

interface EventsRepository {

    suspend fun addFavoriteEvent(event: Event): Outcome<Unit>

    suspend fun removeFavoriteEvent(event: Event): Outcome<Unit>

    suspend fun getFavoriteEvents(): Outcome<List<Event>>

}
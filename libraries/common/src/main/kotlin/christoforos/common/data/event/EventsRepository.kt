package christoforos.common.data.event

import christoforos.common.domain.models.event.Event
import christoforos.common.domain.models.outcome.Outcome

interface EventsRepository {

    suspend fun addFavoriteEvent(event: Event): Outcome<Unit>

    suspend fun removeFavoriteEvent(event: Event): Outcome<Unit>

    suspend fun getFavoriteEvents(): Outcome<List<Event>>

}
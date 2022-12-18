package christoforos.list.domain.interactors

import christoforos.list.data.event.EventsRepository
import christoforos.list.domain.models.Event

class RemoveFavoriteUseCase(private val repository: EventsRepository) {

    suspend operator fun invoke(event: Event) = repository.removeFavoriteEvent(event)

}
package christoforos.common.domain.interactors.favorites

import christoforos.common.data.event.EventsRepository
import christoforos.common.domain.models.event.Event

class RemoveFavoriteUseCase(private val repository: EventsRepository) {

    suspend operator fun invoke(event: Event) = repository.removeFavoriteEvent(event)

}
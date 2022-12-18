package christoforos.list.domain.interactors

import christoforos.list.data.event.EventsRepository
import christoforos.list.domain.models.Event

class AddFavoriteUseCase(private val repository: EventsRepository) {

    suspend operator fun invoke(event: Event) = repository.addFavoriteEvent(event)

}
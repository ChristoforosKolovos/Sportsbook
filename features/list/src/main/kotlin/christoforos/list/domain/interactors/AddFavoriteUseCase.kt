package christoforos.list.domain.interactors

import christoforos.common.data.event.EventsRepository
import christoforos.common.domain.models.event.Event

class AddFavoriteUseCase(private val repository: EventsRepository) {

    suspend operator fun invoke(event: Event) = repository.addFavoriteEvent(event)

}
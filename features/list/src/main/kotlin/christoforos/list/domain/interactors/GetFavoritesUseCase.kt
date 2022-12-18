package christoforos.list.domain.interactors

import christoforos.list.data.event.EventsRepository

class GetFavoritesUseCase(private val repository: EventsRepository) {

    suspend operator fun invoke() = repository.getFavoriteEvents()

}
package christoforos.common.domain.interactors.favorites

import christoforos.common.data.event.EventsRepository

class GetFavoritesUseCase(private val repository: EventsRepository) {

    suspend operator fun invoke() = repository.getFavoriteEvents()

}
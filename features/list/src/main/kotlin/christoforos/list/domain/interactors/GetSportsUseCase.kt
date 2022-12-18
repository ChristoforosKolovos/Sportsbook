package christoforos.list.domain.interactors

import christoforos.common.domain.models.outcome.Outcome
import christoforos.common.domain.models.outcome.OutcomeExtensions.onError
import christoforos.common.domain.models.outcome.OutcomeExtensions.onSuccess
import christoforos.common.domain.models.outcome.OutcomeUtilities.toSuccessfulOutcome
import christoforos.list.data.event.EventsRepository
import christoforos.list.data.sports.SportsRepository
import christoforos.list.domain.models.Event
import christoforos.list.domain.models.Sport

class GetSportsUseCase(
    private val sportsRepository: SportsRepository,
    private val eventsRepository: EventsRepository
) {

    suspend operator fun invoke(): Outcome<List<Sport>> {
        var sports: List<Sport>? = null
        sportsRepository.getSports()
            .onSuccess { sports = it }
            .onError { return it }

        var favoriteEvents: List<Event>? = null
        eventsRepository.getFavoriteEvents()
            .onSuccess { favoriteEvents = it }

        sports?.let { s ->
            s.forEach { sport ->
                sport.events?.forEach { sportEvent ->
                    favoriteEvents?.forEach { favoriteEvent ->
                        if (sportEvent.id == favoriteEvent.id) sportEvent.favorite = true
                    }
                }
                sport.events = sport.events
                    ?.sortedByDescending { it.timestamp }
                    ?.sortedByDescending { it.favorite }
            }
            return sports.toSuccessfulOutcome()
        } ?: run {
            return null.toSuccessfulOutcome()
        }

    }

}
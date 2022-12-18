package christoforos.list.domain.interactors

import christoforos.common.domain.models.outcome.Outcome
import christoforos.common.domain.models.outcome.OutcomeExtensions.onError
import christoforos.common.domain.models.outcome.OutcomeExtensions.onSuccess
import christoforos.common.domain.models.outcome.OutcomeUtilities.toSuccessfulOutcome
import christoforos.list.data.sports.SportsRepository
import christoforos.list.domain.models.Sport

class GetSportsUseCase(private val repository: SportsRepository) {

    suspend operator fun invoke(): Outcome<List<Sport>> {
        return repository.getSports().onSuccess { sports ->
            sports?.forEach { sport ->
                sport.events = sport.events
                    ?.sortedByDescending { it.timestamp }
                    ?.sortedByDescending { it.favorite }
            }.toSuccessfulOutcome()
        }.onError { return it }
    }

}
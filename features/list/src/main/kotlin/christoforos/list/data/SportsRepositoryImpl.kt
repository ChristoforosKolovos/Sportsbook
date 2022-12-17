package christoforos.list.data

import christoforos.api.SportsApi
import christoforos.common.domain.models.outcome.Outcome
import christoforos.common.domain.models.outcome.OutcomeExtensions.mapSuccess
import christoforos.common.domain.models.outcome.OutcomeUtilities.outcomeFromApiCall
import christoforos.list.data.SportsMappers.toSports
import christoforos.list.domain.models.Sport

class SportsRepositoryImpl(private val api: SportsApi) : SportsRepository {

    override suspend fun getSports(): Outcome<List<Sport>> {
        return outcomeFromApiCall{
            api.getSports()
        }.mapSuccess {
            toSports()
        }
    }

}
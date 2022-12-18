package christoforos.list.data.sports

import christoforos.common.domain.models.outcome.Outcome
import christoforos.list.domain.models.Sport

interface SportsRepository {

    suspend fun getSports(): Outcome<List<Sport>>

}
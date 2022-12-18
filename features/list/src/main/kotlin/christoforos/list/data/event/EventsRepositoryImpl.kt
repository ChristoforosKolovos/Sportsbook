package christoforos.list.data.event

import christoforos.common.domain.models.outcome.Outcome
import christoforos.common.domain.models.outcome.OutcomeExtensions.mapSuccess
import christoforos.common.domain.models.outcome.OutcomeUtilities.outcomeFromSqlQuery
import christoforos.database.event.EventDao
import christoforos.list.data.event.EventMappers.toEventEntity
import christoforos.list.data.event.EventMappers.toEvents
import christoforos.list.domain.models.Event

class EventsRepositoryImpl(private val dao: EventDao) : EventsRepository {

    override suspend fun addFavoriteEvent(event: Event): Outcome<Unit> {
        return outcomeFromSqlQuery {
            dao.insertEvent(event.toEventEntity())
        }
    }

    override suspend fun removeFavoriteEvent(event: Event): Outcome<Unit> {
        return outcomeFromSqlQuery {
            dao.deleteEvent(event.toEventEntity())
        }
    }

    override suspend fun getFavoriteEvents(): Outcome<List<Event>> {
        return outcomeFromSqlQuery {
            dao.getEvents()
        }.mapSuccess {
            toEvents()
        }
    }

}
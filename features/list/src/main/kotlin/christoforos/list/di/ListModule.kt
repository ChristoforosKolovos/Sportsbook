package christoforos.list.di

import christoforos.api.SportsApi
import christoforos.common.data.event.EventsRepository
import christoforos.list.data.sports.SportsRepository
import christoforos.list.data.sports.SportsRepositoryImpl
import christoforos.list.domain.interactors.AddFavoriteUseCase
import christoforos.list.domain.interactors.GetSportsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ListModule {

    @Provides
    fun provideAddFavoriteUseCase(eventsRepository: EventsRepository): AddFavoriteUseCase =
        AddFavoriteUseCase(eventsRepository)

    @Provides
    fun provideGetSportsUseCase(
        sportsRepository: SportsRepository,
        eventsRepository: EventsRepository
    ): GetSportsUseCase =
        GetSportsUseCase(sportsRepository, eventsRepository)

    @Provides
    fun provideSportsRepository(sportsApi: SportsApi): SportsRepository =
        SportsRepositoryImpl(sportsApi)

}
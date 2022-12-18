package christoforos.list.di

import christoforos.api.SportsApi
import christoforos.database.event.EventDao
import christoforos.list.data.event.EventsRepository
import christoforos.list.data.event.EventsRepositoryImpl
import christoforos.list.data.sports.SportsRepository
import christoforos.list.data.sports.SportsRepositoryImpl
import christoforos.list.domain.interactors.AddFavoriteUseCase
import christoforos.list.domain.interactors.GetFavoritesUseCase
import christoforos.list.domain.interactors.GetSportsUseCase
import christoforos.list.domain.interactors.RemoveFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ListModule {

    @Provides
    fun provideRemoveFavoriteUseCase(eventsRepository: EventsRepository): RemoveFavoriteUseCase =
        RemoveFavoriteUseCase(eventsRepository)

    @Provides
    fun provideGetFavoritesUseCase(eventsRepository: EventsRepository): GetFavoritesUseCase =
        GetFavoritesUseCase(eventsRepository)

    @Provides
    fun provideAddFavoriteUseCase(eventsRepository: EventsRepository): AddFavoriteUseCase =
        AddFavoriteUseCase(eventsRepository)

    @Provides
    fun provideEventsRepository(eventsDao: EventDao): EventsRepository =
        EventsRepositoryImpl(eventsDao)

    @Provides
    fun provideGetSportsUseCase(sportsRepository: SportsRepository): GetSportsUseCase =
        GetSportsUseCase(sportsRepository)

    @Provides
    fun provideSportsRepository(sportsApi: SportsApi): SportsRepository =
        SportsRepositoryImpl(sportsApi)

}
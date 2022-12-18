package christoforos.common.di

import christoforos.common.data.event.EventsRepository
import christoforos.common.data.event.EventsRepositoryImpl
import christoforos.common.domain.interactors.favorites.GetFavoritesUseCase
import christoforos.common.domain.interactors.favorites.RemoveFavoriteUseCase
import christoforos.database.event.EventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Provides
    fun provideRemoveFavoriteUseCase(eventsRepository: EventsRepository): RemoveFavoriteUseCase =
        RemoveFavoriteUseCase(eventsRepository)

    @Provides
    fun provideGetFavoritesUseCase(eventsRepository: EventsRepository): GetFavoritesUseCase =
        GetFavoritesUseCase(eventsRepository)

    @Provides
    fun provideEventsRepository(eventsDao: EventDao): EventsRepository =
        EventsRepositoryImpl(eventsDao)

}
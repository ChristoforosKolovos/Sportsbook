package christoforos.list.di

import christoforos.api.SportsApi
import christoforos.list.data.SportsRepository
import christoforos.list.data.SportsRepositoryImpl
import christoforos.list.domain.interactors.GetSportsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ListModule {

    @Provides
    fun provideGetSportsUseCase(sportsRepository: SportsRepository): GetSportsUseCase =
        GetSportsUseCase(sportsRepository)

    @Provides
    fun provideSportsRepository(sportsApi: SportsApi): SportsRepository =
        SportsRepositoryImpl(sportsApi)

}
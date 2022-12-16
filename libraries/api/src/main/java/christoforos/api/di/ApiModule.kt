package christoforos.api.di

import christoforos.api.ApiProvider
import christoforos.api.SportsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    fun provideApi(): SportsApi {
        return ApiProvider().getApi()
    }

}
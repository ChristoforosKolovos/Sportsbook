package christoforos.sportsbook.di

import christoforos.navigation.FragmentNavigator
import christoforos.sportsbook.AppFragmentNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAppFragmentNavigator(): FragmentNavigator = AppFragmentNavigator()

}

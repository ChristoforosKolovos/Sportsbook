package christoforos.navigation.di

import christoforos.navigation.FragmentNavigator
import christoforos.navigation.NavigatorProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    fun provideAppFragmentNavigator(fragmentNavigator: FragmentNavigator): NavigatorProvider =
        NavigatorProvider(fragmentNavigator)

}
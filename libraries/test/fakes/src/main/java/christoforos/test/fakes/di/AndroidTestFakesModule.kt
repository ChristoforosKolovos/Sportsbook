package christoforos.test.fakes.di

import christoforos.navigation.FragmentNavigator
import christoforos.test.fakes.FakeAppFragmentNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AndroidTestFakesModule {

    @Provides
    fun provideFakeAppFragmentNavigator(): FragmentNavigator =
        FakeAppFragmentNavigator()
}
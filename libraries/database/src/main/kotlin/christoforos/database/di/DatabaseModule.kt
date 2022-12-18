package christoforos.database.di

import android.app.Application
import christoforos.database.DatabaseProvider
import christoforos.database.event.EventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideEventDao(application: Application): EventDao =
        DatabaseProvider().getDatabase(application)

}
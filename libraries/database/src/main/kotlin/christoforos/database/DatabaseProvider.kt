package christoforos.database

import android.app.Application
import androidx.room.Room
import timber.log.Timber
import java.util.concurrent.Executors

class DatabaseProvider {

    companion object {
        private const val DATABASE_NAME = "database"
        private const val LOG_TAG = "DATABASE"
        private const val LOG_MSG = "SQL Query: %s | SQL Args: %s"
    }

    fun getDatabase(app: Application) =
        Room.databaseBuilder(
            app,
            Database::class.java,
            DATABASE_NAME
        ).setQueryCallback(
            { sqlQuery, bindArgs ->
                Timber.tag(LOG_TAG).d(LOG_MSG, sqlQuery, bindArgs)
            },
            Executors.newSingleThreadExecutor()
        ).build().dao

}
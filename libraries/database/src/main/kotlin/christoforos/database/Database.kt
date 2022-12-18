package christoforos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import christoforos.database.event.EventDao
import christoforos.database.event.EventEntity

@Database(
    entities = [EventEntity::class],
    version = 1
)
abstract class Database : RoomDatabase() {

    abstract val dao: EventDao

}
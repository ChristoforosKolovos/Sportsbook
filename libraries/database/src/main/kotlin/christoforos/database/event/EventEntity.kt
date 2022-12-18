package christoforos.database.event

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val description: String?,
    val sportId: String?,
    val header: String?,
    val timestamp: String?,
    var favorite: Boolean = false,

    )

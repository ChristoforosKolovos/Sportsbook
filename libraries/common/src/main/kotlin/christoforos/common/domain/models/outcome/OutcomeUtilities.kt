package christoforos.common.domain.models.outcome

import android.database.sqlite.SQLiteException
import retrofit2.Response

object OutcomeUtilities {

    inline fun <T> outcomeFromSqlQuery(block: () -> T): Outcome<T> {
        return try {
            block().toSuccessfulOutcome()
        } catch (e: SQLiteException) {
            Outcome.Error.DatabaseError(e.message)
        }
    }

    inline fun <T> outcomeFromApiCall(block: () -> Response<T>): Outcome<T> {
        val result = block()

        return if (result.isSuccessful) result.body().toSuccessfulOutcome()
        else Outcome.Error.ApiError(result.message())

    }

    fun <T> T?.toSuccessfulOutcome() = Outcome.Success(this)
}
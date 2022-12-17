package christoforos.common.domain.models.outcome

import retrofit2.Response

object OutcomeUtilities {

    inline fun <T> outcomeFromApiCall(block: () -> Response<T>): Outcome<T> {
        val result = block()

        return if (result.isSuccessful) result.body().toSuccessfulOutcome()
        else Outcome.Error.ApiError(result.message())

    }

    fun <T> T?.toSuccessfulOutcome() = Outcome.Success(this)
}
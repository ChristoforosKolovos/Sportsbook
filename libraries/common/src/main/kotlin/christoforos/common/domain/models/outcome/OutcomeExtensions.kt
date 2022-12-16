package christoforos.common.domain.models.outcome

import christoforos.common.domain.models.outcome.OutcomeUtilities.toSuccessfulOutcome

object OutcomeExtensions {

    inline fun <T> Outcome<T>.onSuccess(action: (T?) -> Unit): Outcome<T> = when (this) {
        is Outcome.Success -> apply { action(value) }
        is Outcome.Error -> this
    }

    inline fun <T> Outcome<T>.onError(action: (Outcome.Error) -> Unit): Outcome<T> = when (this) {
        is Outcome.Success -> this
        is Outcome.Error -> apply { action(this) }
    }

    inline fun <T> Outcome<T>.onAny(action: () -> Unit): Outcome<T> {
        action()
        return this
    }

    inline fun <T, R> Outcome<T>.mapSuccess(mapper: T.() -> R): Outcome<R> {
        return when (this) {
            is Outcome.Success -> value?.mapper().toSuccessfulOutcome()
            is Outcome.Error -> this
        }
    }

    inline fun <T> Outcome<T>.mapError(error: Outcome.Error.() -> Outcome.Error): Outcome<T> {
        return when (this) {
            is Outcome.Success -> this
            is Outcome.Error -> error()
        }
    }

}
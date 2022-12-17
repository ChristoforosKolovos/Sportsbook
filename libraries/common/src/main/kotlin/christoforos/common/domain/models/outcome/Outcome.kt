package christoforos.common.domain.models.outcome

sealed interface Outcome<out T> {

    class Success<T>(val value: T?) : Outcome<T>

    sealed interface Error : Outcome<Nothing> {
        data class GeneralError(val message: String? = null) : Error
        data class ApiError(val message: String? = null) : Error
    }

}
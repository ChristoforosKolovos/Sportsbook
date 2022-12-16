package christoforos.mvvmi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<S : UiState, E : UiEvent, F : UiEffect>(
    initialState: S
) : ViewModel() {

    companion object {
        private const val LOG_TAG = "VIEWMODEL"
        private const val LOG_MSG_EVENT = "Event: %s"
        private const val LOG_MSG_STATE = "State: %s"
        private const val LOG_MSG_EFFECT = "Effect: %s"
    }

    private val state: MutableStateFlow<S> = MutableStateFlow(initialState)

    private val effect = Channel<F>()

    val currentState: S
        get() = state.value

    fun state(): StateFlow<S> = state

    fun effect(): Flow<F> = effect.receiveAsFlow()

    fun sendEvent(event: E) = viewModelScope.launch {
        log(event)
        handleEvent(event)
    }

    protected fun setState(reduce: S.() -> S) {
        val newState = state.value.reduce()
        log(newState)
        state.value = newState
    }

    protected fun setEffect(builder: () -> F) {
        val effectValue = builder()
        log(effectValue)
        viewModelScope.launch { effect.send(effectValue) }
    }

    protected abstract suspend fun handleEvent(event: E)

    private fun log(event: E) = Timber.tag(LOG_TAG).d(LOG_MSG_EVENT, event)

    private fun log(state: S) = Timber.tag(LOG_TAG).d(LOG_MSG_STATE, state)

    private fun log(effect: F) = Timber.tag(LOG_TAG).d(LOG_MSG_EFFECT, effect)

}
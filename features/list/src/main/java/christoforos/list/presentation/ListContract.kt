package christoforos.list.presentation

import androidx.annotation.StringRes
import christoforos.list.domain.models.Sport
import christoforos.mvvmi.UiEffect
import christoforos.mvvmi.UiEvent
import christoforos.mvvmi.UiState
import christoforos.list.domain.models.Event as SportEvent

object ListContract {

    sealed interface Effect : UiEffect {
        data class ShowDialog(@StringRes val stringResourceId: Int) : Effect
    }

    sealed interface Event : UiEvent {
        data class OnFavoriteEvent(val event: SportEvent) : Event
    }

    data class State(
        val sports: List<Sport>,
        val screenState: ScreenState
    ) : UiState

    sealed interface ScreenState {
        object Loading : ScreenState
        data class Results(val sports: List<Sport>) : ScreenState
        object NoResults : ScreenState
        object Error : ScreenState
    }

}